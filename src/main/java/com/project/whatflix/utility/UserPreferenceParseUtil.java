package com.project.whatflix.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.whatflix.model.UserPreference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UserPreferenceParseUtil {
  static Logger log = Logger.getLogger(UserPreferenceParseUtil.class.getName());

  /**
   * Parse the Json Strings to create a UserPreference instances and stores them in HashMap with Id as the key.
   *
   * @param individualUserPreferenceJson
   * @return
   */
  public static HashMap<String, UserPreference> fromJson(org.json.simple.JSONObject individualUserPreferenceJson) {
    HashMap<String, UserPreference> userPreferenceHashMap = new HashMap<>();
    Map.Entry userPreferenceEntry = (Map.Entry) individualUserPreferenceJson.entrySet().iterator().next();
    try {
      org.json.simple.JSONObject userPreference = (org.json.simple.JSONObject) individualUserPreferenceJson.get(userPreferenceEntry.getKey());
      userPreferenceHashMap.put((String) userPreferenceEntry.getKey(), parse(userPreference));
      log.info("User preference read for user id: " + userPreferenceEntry.getKey());
    } catch (Exception e) {
      log.info(e.getMessage());
    }
    return userPreferenceHashMap;
  }

  private static UserPreference parse(org.json.simple.JSONObject userPreferenceJson) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    UserPreference userPreference = new UserPreference();
    userPreference = mapper.readValue(userPreferenceJson.toString(), UserPreference.class);
    return userPreference;
  }
}
