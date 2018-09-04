package com.project.whatflix.utility;

import com.project.whatflix.model.UserPreference;

import java.util.ArrayList;
import java.util.List;

public class SQLQueryUtil {

  public static String addParametersFromUserPreferncesInASeriesOfOrForALikeQuery(UserPreference userPreference, String type) {
    StringBuilder queryPart = new StringBuilder(" ");
    List<String> favourites;
    switch (type) {
      case "credit.crew":
        favourites = userPreference.getFavourite_directors();
        break;
      case "credit.cast":
        favourites = userPreference.getFavourite_actors();
        break;
      default:
        favourites = new ArrayList<>();
    }

    for (String favourite : favourites) {
      queryPart.append(" " + type + " like " + getParameterWrappedToUseInQuery(favourite) + OR_STRING);
    }
    return queryPart.toString();
  }



  public static String createtQueryForSearchStringsForTitleCrewCast(String[] searchStrings) {
    StringBuilder sql = new StringBuilder(" ");
    for (String queryString : searchStrings) {
      queryString = getParameterWrappedToUseInQuery(queryString);
      sql.append(
          " credit.title like " + queryString + SQLQueryUtil.OR_STRING +
              " credit.cast like " + queryString + SQLQueryUtil.OR_STRING +
              " credit.crew like " + queryString + SQLQueryUtil.OR_STRING);
    }
    return sql.toString();
  }

  public static String getSelectFrom(String className) {
    return " from " + className + " ";
  }
  public static String getParameterWrappedToUseInQuery(String input) {
    return " '%" + input.trim() + "%' ";
  }
  public static String getWhereClause(String className) {
    return " " + className + " Where ";
  }
  public static String[] preferenceParameters = {"credit.crew", "credit.cast"};
  public static String OR_STRING = " or ";
  public static String getOrderBy(String field, boolean ascending) {
    return " order by " + field + " " + (ascending ? "ASC" : "DESC");
  }
}
