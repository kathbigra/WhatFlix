package com.project.whatflix.services;

import com.project.whatflix.model.UserPreference;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServicesForUser {

  @GetMapping(path = "/movies/getMoviesForUser")
  public String[] getMoviesForUserByuserId(
      @RequestParam(value = "user") String userId, @RequestParam(value = "search") String queryString) {
    String[] preferredMovies = {};
    return preferredMovies;
  }

  @GetMapping(path = "/movies/getMoviePreferenceForUsers")
  public String[] getMoviesForUsersBasedOnPreference(  ) {
    try {
      String jsonString = "{\"100\": {\"preferred_languages\": [\"English\",\"Spanish\"],\"favourite_actors\": [\"Denzel Washington\",\"Kate Winslet\",\"Emma Suárez\",\"Tom Hanks\"],\"favourite_directors\": [\"Steven Spielberg\",\"Martin Scorsese\",\"Pedro Almodóvar\"]}}";
      UserPreference.fromJson(new JSONObject(jsonString));
      String[] preferredMovies = {};
      return preferredMovies;
    } catch (Exception e) {

    }
    return null;
  }
}
