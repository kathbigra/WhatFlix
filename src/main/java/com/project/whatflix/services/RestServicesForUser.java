package com.project.whatflix.services;

import com.project.whatflix.controller.RESTServiceController;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class RestServicesForUser {

  @Autowired
  RESTServiceController controller;

  @GetMapping(path = "/movies/user/{userId}/search")
  public String[] getMoviesForUserByuserId(
      @PathVariable("userId") String userId,
      @RequestParam(value = "text") String queryString) {
    log.info("Request for movies by user id: " + userId);
    String[] preferredMovies = controller.getUserRequestedMovies(userId, queryString);
    return preferredMovies;
  }

  int PREFERRED_LIMT = 3;
  private String PRIORITIZE_BY="movie.vote_average";

  @GetMapping(path = "/movies/users")
  public String getMoviesForUsersBasedOnPreference() {
    log.info("Request for movies for users on preference");
    JSONArray preferredMovies = controller.getPreferredMovies(PREFERRED_LIMT, PRIORITIZE_BY);
    return preferredMovies.toString();
  }

  Logger log = Logger.getLogger(RestServicesForUser.class.getName());
}
