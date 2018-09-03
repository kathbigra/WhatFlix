package com.project.whatflix.services;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RestServicesForUser {

  @Autowired
  UserPreferenceConfiguration userPreferences;

  @GetMapping(path = "/movies/getMoviesForUser")
  public String[] getMoviesForUserByuserId(
      @RequestParam(value = "user") String userId, @RequestParam(value = "search") String queryString) {
    log.info("Request for movies by user id: " + userId);
    String[] preferredMovies = userPreferences.getUserRequestedMovies(userId, queryString);
    return preferredMovies;
  }

  @GetMapping(path = "/movies/getMoviePreferenceForUsers")
  public String getMoviesForUsersBasedOnPreference() {
    log.info("Request for movies for users on preference");
    try {
      JSONArray preferredMovies = new JSONArray();
      userPreferences.getUserPreferenceData().forEach((userId, userPreference) -> {
        //Fetch the movie list as per the user preference.
        try {
          JSONObject userdata = new JSONObject();
          userdata.put("user", userId);
          userdata.put("movies",
              userPreferences.getUserPreferredMovies(userPreference));
          preferredMovies.put(userdata);
        } catch (Exception e) {
          log.log(Level.ALL, e.getMessage(), e);
        }
      });
      return preferredMovies.toString();
    } catch (Exception e) {
      log.log(Level.ALL, e.getMessage(), e);
    }
    return null;
  }

  /*static final String USER_PREFERENCE_URL = "https://gist.githubusercontent.com/dhanush/9409f9afe2d15956dd508d95b933726f/raw/abfe28ea931286086d364fb0be82a22a949df9ac/user_preferences.json";

  private void parseUserPreference(JSONArray allUserPrefernces) {
    try {

      ClassLoader classLoader = getClass().getClassLoader();
      File file = new File(classLoader.getResource("user_preference.json").getFile());
      InputStream in = new URL(USER_PREFERENCE_URL).openStream();

      Files.copy(in, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
      for (int i = 0; i < allUserPrefernces.length(); i++) {
        JSONObject individualUserPreference = allUserPrefernces.getJSONObject(i);
        // HashMap<String, UserPreference> userPreferenceInstance =
        //   UserPreference.fromJson(individualUserPreference);
      }
    } catch (Exception e) {
      log.log(Level.ALL, e.getMessage(), e);
    }
  }*/

  Logger log = Logger.getLogger(RestServicesForUser.class.getName());
}
