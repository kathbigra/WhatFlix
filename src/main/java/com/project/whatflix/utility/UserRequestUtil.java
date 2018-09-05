package com.project.whatflix.utility;

import com.project.whatflix.controller.RESTServiceController;
import com.project.whatflix.model.Credits;
import com.project.whatflix.model.Movies;
import com.project.whatflix.model.UserPreference;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.project.whatflix.utility.SQLQueryUtil.*;

public class UserRequestUtil {
  private final EntityManager entityManager;

  public UserRequestUtil(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  public List<Credits> getCreditListBasedOnPreference(UserPreference userPreference) {
    StringBuilder sql = new StringBuilder(getSelectFrom(Credits.class.getName())+getWhereClause("credit"));
    sql.append(addParametersFromUserPreferncesInASeriesOfOrForALikeQuery(userPreference, "credit.crew"));
    String sqlQuery= sql.substring(0, sql.length() - SQLQueryUtil.OR_STRING.length()) + getOrderBy("title",true);
    Query query = entityManager.createQuery(sqlQuery, Credits.class);
    return query.getResultList();
  }

  public List<Credits> getCreditListBasedOnUserSearch(String[] searchStrings) {
    StringBuilder sql = new StringBuilder(getSelectFrom(Credits.class.getName())+getWhereClause("credit"));
    sql.append(createtQueryForSearchStringsForTitleCrewCast(searchStrings));
    String sqlQuery= sql.substring(0, sql.length() - SQLQueryUtil.OR_STRING.length()) + getOrderBy("title",true);
    Query query = entityManager.createQuery(sqlQuery, Credits.class);
    return query.getResultList();
  }



  public String[] getTitlesInSortedOrder(List<Credits> preferredList, List<Credits> serachedList) {
    preferredList.addAll(serachedList);
    ArrayList<String> movieNameList = new ArrayList<>();
    for (Credits tempCredit : preferredList) {
      movieNameList.add(tempCredit.getTitle());
    }
    log.info("Final list contains: " + movieNameList.size() + " movies.");
    return movieNameList.toArray(new String[0]);
  }Logger log = Logger.getLogger(UserRequestUtil.class.getName());

}
