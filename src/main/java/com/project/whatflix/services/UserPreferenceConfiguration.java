package com.project.whatflix.services;


import com.project.whatflix.model.Credits;
import com.project.whatflix.model.UserPreference;
import com.project.whatflix.utility.FetchUserPreferenceUtil;
import com.project.whatflix.utility.UserPreferenceParseUtil;
import com.project.whatflix.utility.UserRequestUtil;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserPreferenceConfiguration implements UserPreferenceConfigurationInterface {

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
   * @return List of movies for the user.
   */
  @Override
  public org.codehaus.jettison.json.JSONArray getUserPreferredMovies(UserPreference userPreference) {
    FetchUserPreferenceUtil fetchUserPreferenceUtil = new FetchUserPreferenceUtil(entityManager);
    List<Credits> creditsOfActorChoice = fetchUserPreferenceUtil.fetchPreferedList(userPreference, "cast");
    List<Credits> creditsOfDirectorChoice = fetchUserPreferenceUtil.fetchPreferedList(userPreference, "crew");
    if (creditsOfDirectorChoice.size() < 1 && creditsOfActorChoice.size() < 1)
      //If nothing matches the preference return blank list.
      return new org.codehaus.jettison.json.JSONArray();
    return fetchUserPreferenceUtil.findPreferenceMovies(creditsOfActorChoice, creditsOfDirectorChoice);
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
    String[] searchStrings = search.split(",");
    UserRequestUtil userRequestUtil = new UserRequestUtil(entityManager);
    List<Credits> creditsList = userRequestUtil.getCreditListBasedOnUserSearch(searchStrings);
    return userRequestUtil.sortListAsPerUserPreference(creditsList, getUserPreferenceForUser(userId));
  }

  Logger log = Logger.getLogger(UserPreferenceConfiguration.class.getName());

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
