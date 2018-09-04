package com.project.whatflix.controller;

import com.project.whatflix.model.UserPreference;
import org.codehaus.jettison.json.JSONArray;

import java.util.HashMap;

public interface RESTServiceControllerInterface {
  public HashMap<String, UserPreference> getUserPreferenceData();
  public UserPreference getUserPreferenceForUser(String userId);
  public String[] getUserRequestedMovies(String userId, String search);
  public JSONArray getPreferredMovies(int PREFERRED_LIMT, String PRIORITIZE_BY);
}
