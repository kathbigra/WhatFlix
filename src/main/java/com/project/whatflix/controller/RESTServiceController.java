package com.project.whatflix.controller;


import com.project.whatflix.model.Credits;
import com.project.whatflix.model.UserPreference;
import com.project.whatflix.utility.FetchUserPreferenceUtil;
import com.project.whatflix.utility.UserPreferenceParseUtil;
import com.project.whatflix.utility.UserRequestUtil;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RESTServiceController implements RESTServiceControllerInterface {

  @Autowired
  EntityManager entityManager;

  /**
   * This method is called at start to parse the user_preference.json file and store it in-memory.
   */
  @PostConstruct
  public void parseUserPreferences() {
    try {
      ClassPathResource classPathResource = new ClassPathResource("user_preference.json");
      InputStream inputStream = classPathResource.getInputStream();
      File tempFile = File.createTempFile("temp", ".json");
      try {
        FileUtils.copyInputStreamToFile(inputStream, tempFile);
      } finally {
        IOUtils.closeQuietly(inputStream);
      }
      log.info("Reading user preference from: " + tempFile.getAbsolutePath());
      if (!tempFile.exists()) log.warning("No user config found.");
      org.json.simple.JSONArray usersPrefrences = (org.json.simple.JSONArray) new JSONParser().parse(new FileReader(tempFile));
      for (int i = 0; i < usersPrefrences.size(); i++) {
        org.json.simple.JSONObject individualUserPreference = (org.json.simple.JSONObject) usersPrefrences.get(i);
        userPreferenceData.putAll(
            UserPreferenceParseUtil.fromJson(individualUserPreference));

      }
    } catch (Exception e) {
      log.log(Level.WARNING, e.getMessage());
    }
    log.info("User preference json parsed successfully with " + userPreferenceData.size() + " records.");
  }

  /**
   * Get set of movies recommended to every user.
   *
   * @param limit
   * @param priority
   * @return
   */
  @Override
  public JSONArray getPreferredMovies(int limit, String priority) {
    JSONArray preferredMovies = new JSONArray();
    getUserPreferenceData().forEach((userId, userPreference) -> {
      log.info("Fetching data for userId: " + userId);
      try {
        JSONObject userdata = new JSONObject();
        userdata.put("user", userId);
        userdata.put("movies", (new FetchUserPreferenceUtil(entityManager)).getTopMovies(userPreference, limit, priority));
        preferredMovies.put(userdata);
      } catch (Exception e) {
        log.log(Level.WARNING, "UserId: " + userId + " " + e.getMessage(), e);
      }
    });
    return preferredMovies;
  }

  /**
   * Get list of movies requested by user using his/her search query.
   *
   * @param userId
   * @param search
   * @return
   */
  @Override
  public String[] getUserRequestedMovies(String userId, String search) {
    if (!getUserPreferenceData().containsKey(userId)) {
      log.info("User id not found: " + userId);
      return new String[0];
    }
    String[] searchStrings = search.split(",");
    UserRequestUtil userRequestUtil = new UserRequestUtil(entityManager);
    List<Credits> searchedList = userRequestUtil.getCreditListBasedOnUserSearch(searchStrings);
    log.info("Searched list size: " + searchedList.size());
    List<Credits> preferredList = userRequestUtil.getCreditListBasedOnPreference(getUserPreferenceForUser(userId));
    log.info("Preferred list size: " + preferredList.size());
    preferredList.retainAll(searchedList);
    searchedList.removeAll(preferredList);
    return userRequestUtil.getTitlesInSortedOrder(preferredList, searchedList);
  }

  Logger log = Logger.getLogger(RESTServiceController.class.getName());

  private HashMap<String, UserPreference> userPreferenceData = new HashMap<>();

  /**
   * Get all the user preference data as a map.
   *
   * @return
   */
  @Override
  public HashMap<String, UserPreference> getUserPreferenceData() {
    return userPreferenceData;
  }

  /**
   * Get UserPreference Data of a particular user
   *
   * @param userId
   * @return
   */
  @Override
  public UserPreference getUserPreferenceForUser(String userId) {
    return userPreferenceData.get(userId);
  }

}
