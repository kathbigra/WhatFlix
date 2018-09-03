package com.project.whatflix.services;

import com.project.whatflix.model.UserPreference;
import org.codehaus.jettison.json.JSONArray;

import java.util.HashMap;

public interface UserPreferenceConfigurationInterface {
  public HashMap<String, UserPreference> getUserPreferenceData();

  public UserPreference getUserPreferenceForUser(String userId);

  public String[] getUserRequestedMovies(String userId, String search);
  public JSONArray getUserPreferredMovies(UserPreference userPreference);


}
