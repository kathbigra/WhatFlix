package com.project.whatflix.utility;

import com.project.whatflix.model.Credits;
import com.project.whatflix.model.Movies;
import com.project.whatflix.model.UserPreference;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.project.whatflix.utility.FetchUserPreferenceUtil.getParameter;

public class UserRequestUtil {
  private final EntityManager entityManager;

  public UserRequestUtil(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Credits> getCreditListBasedOnUserSearch(String[] searchStrings) {
    String sql = "from " + Credits.class.getName() + " credit Where ";
    for (String queryString : searchStrings) {
      //Add all the query strings in serach parameters. We are checking here for title cast and crew.
      queryString = getParameter(queryString);
      sql = sql
          + " credit.title like " + queryString
          + " or credit.cast like " + queryString +
          " or credit.crew like " + queryString + " or ";
    }
    sql = sql.substring(0, sql.length() - 3) + " order by title ASC";
    Query query = entityManager.createQuery(sql, Credits.class);
    return query.getResultList();
  }

  /**
   * Method to sort the returned list as per user preference
   *
   * @param creditsList
   * @param userPreference
   * @return
   */
  public String[] sortListAsPerUserPreference(List<Credits> creditsList, UserPreference userPreference) {
    ArrayList<String> preferredList = new ArrayList<>();
    ArrayList<String> suggestedList = new ArrayList<>();
    for (Credits credits : creditsList) {
      //Iterate all the fetched movies.
      String sql = "from " + Movies.class.getName() + " movie Where movie.id = :id ";
      Query query = entityManager.createQuery(sql, Movies.class);
      query.setParameter("id", credits.getMovie_id());
      if (query.getResultList().size() > 0) {
        Movies tempMovie = (Movies) query.getSingleResult();
        if (isPreferred(tempMovie, credits, userPreference))
          //For each movie check if it matches the user preference
          preferredList.add(tempMovie.getTitle());
        else suggestedList.add(tempMovie.getTitle());
      }
    }
    preferredList.addAll(suggestedList);
    //Merge both the sorted list
    return preferredList.toArray(new String[0]);
  }


  private boolean isPreferred(Movies movie, Credits credits, UserPreference userPreference) {
    if (isMatch(movie.getSpoken_languages(), userPreference.getPreferred_languages())) return true;
    if (isMatch(credits.getCast(), userPreference.getFavourite_actors())) return true;
    if (isDirectorMatch(credits.getCrew(), userPreference.getFavourite_directors())) return true;
    return false;
  }

  /**
   * Check if director of the movie is there in user preference.
   *
   * @param crew
   * @param favourite_directors
   * @return
   */
  private boolean isDirectorMatch(String crew, List<String> favourite_directors) {
    try {
      org.codehaus.jettison.json.JSONArray crewArray = new org.codehaus.jettison.json.JSONArray(crew);
      for (int i = 0; i < crewArray.length(); i++) {
        org.codehaus.jettison.json.JSONObject crewDetails = crewArray.getJSONObject(i);
        if (crewDetails.getString("job").equalsIgnoreCase("Director")) {
          for (String director : favourite_directors)
            if (crewDetails.getString("name").equalsIgnoreCase(director)) return true;
          return false;
        }
      }
    } catch (Exception e) {
      return false;
    }
    return false;
  }

  /**
   * Check if actors of the movie is there in user preference.
   *
   * @param input
   * @param favourites
   * @return
   */
  private boolean isMatch(String input, List<String> favourites) {
    for (String temp : favourites)
      if (input.contains(temp)) return true;
    return false;
  }
}
