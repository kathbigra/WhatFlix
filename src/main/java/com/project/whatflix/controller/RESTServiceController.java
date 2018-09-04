package com.project.whatflix.controller;


import com.project.whatflix.model.Credits;
import com.project.whatflix.model.UserPreference;
import com.project.whatflix.utility.FetchUserPreferenceUtil;
import com.project.whatflix.utility.UserPreferenceParseUtil;
import com.project.whatflix.utility.UserRequestUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileReader;
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
      File jsonFile = ResourceUtils.getFile("classpath:user_preference.json");
      org.json.simple.JSONArray usersPrefrences = (org.json.simple.JSONArray) new JSONParser().parse(new FileReader(jsonFile));
      for (int i = 0; i < usersPrefrences.size(); i++) {
        org.json.simple.JSONObject individualUserPreference = (org.json.simple.JSONObject) usersPrefrences.get(i);
        userPreferenceData.putAll(
            UserPreferenceParseUtil.fromJson(individualUserPreference));
      }
    } catch (Exception e) {
      log.log(Level.ALL, e.getMessage());

    }
    log.info("User preference json parsed successfully");
  }

  /**
   * Fetch top 3 movies for the particular user in a JsonArray.
   *
   * @param userPreference
   * @param limit
   * @param priority
   * @return List of movies for the user.
   */
  private org.codehaus.jettison.json.JSONArray getUserPreferredMovies(UserPreference userPreference, int limit, String priority) {
    FetchUserPreferenceUtil fetchUserPreferenceUtil = new FetchUserPreferenceUtil(entityManager);
    return fetchUserPreferenceUtil.getTopMovies(userPreference, limit,priority);
  }

  @Override
  public JSONArray getPreferredMovies(int PREFERRED_LIMT, String priority) {
    JSONArray preferredMovies = new JSONArray();
    getUserPreferenceData().forEach((userId, userPreference) -> {
      try {
        JSONObject userdata = new JSONObject();
        userdata.put("user", userId);
        userdata.put("movies",
            getUserPreferredMovies(userPreference, PREFERRED_LIMT,priority));
        preferredMovies.put(userdata);
      } catch (Exception e) {
        log.log(Level.ALL, e.getMessage(), e);
      }
    });
    return preferredMovies;
  }

  /**
   * Method to fetch requested movies based on user search query.
   *
   * @param userId
   * @param search
   * @return Array of movies based on search parameters.
   */
  @Override
  public String[] getUserRequestedMovies(String userId, String search) {
    if(!getUserPreferenceData().containsKey(userId))return new String[0];
    String[] searchStrings = search.split(",");
    UserRequestUtil userRequestUtil = new UserRequestUtil(entityManager);
    List<Credits> searchedList = userRequestUtil.getCreditListBasedOnUserSearch(searchStrings);
    List<Credits> preferredList = userRequestUtil.getCreditListBasedOnPreference(getUserPreferenceForUser(userId));
    preferredList.retainAll(searchedList);
    searchedList.removeAll(preferredList);
    return userRequestUtil.getTitlesInSortedOrder(preferredList, searchedList);
  }

  Logger log = Logger.getLogger(RESTServiceController.class.getName());

  private HashMap<String, UserPreference> userPreferenceData = new HashMap<>();

  @Override
  public HashMap<String, UserPreference> getUserPreferenceData() {
    return userPreferenceData;
  }

  @Override
  public UserPreference getUserPreferenceForUser(String userId) {
    return userPreferenceData.get(userId);
  }

}
