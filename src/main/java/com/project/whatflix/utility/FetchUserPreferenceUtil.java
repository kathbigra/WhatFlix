package com.project.whatflix.utility;

import com.project.whatflix.model.Credits;
import com.project.whatflix.model.Movies;
import com.project.whatflix.model.UserPreference;
import org.codehaus.jettison.json.JSONArray;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

import static com.project.whatflix.utility.SQLQueryUtil.*;

public class FetchUserPreferenceUtil {
  private final EntityManager entityManager;

  public FetchUserPreferenceUtil(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  public JSONArray getTopMovies(UserPreference userPreference, int limit, String priority) {
    StringBuilder sql = new StringBuilder("select title " + getSelectFrom(Movies.class.getName()) +
        getWhereClause("movie") + " movie.id IN (" +
        getSelectFrom(Credits.class.getName()) + getWhereClause("credit"));
    for (String type : preferenceParameters) {
      sql.append(addParametersFromUserPreferncesInASeriesOfOrForALikeQuery(userPreference, type));
    }
    String sqlQuery = sql.substring(0, sql.length() - OR_STRING.length()) + ") " + getOrderBy(priority, false);
    Query query = entityManager.createQuery(sqlQuery);
    query.setMaxResults(limit);
    List<String> suggestionList = query.getResultList();
    Collections.sort(suggestionList);
    return new JSONArray(suggestionList);
  }


}
