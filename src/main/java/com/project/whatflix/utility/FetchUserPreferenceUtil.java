package com.project.whatflix.utility;

import com.project.whatflix.model.Credits;
import com.project.whatflix.model.UserPreference;
import org.codehaus.jettison.json.JSONArray;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FetchUserPreferenceUtil {
  private final EntityManager entityManager;

  public FetchUserPreferenceUtil(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Credits> fetchPreferedList(UserPreference userPreference, String type) {
    String queryString = "from " + Credits.class.getName() + " credit Where ";
    //Fetch the user preference movies based on preference type
    List<String> favourites;
    String parameter = null;
    switch (type) {
      case "cast":
        parameter = "credit.cast";
        favourites = userPreference.getFavourite_actors();
        break;
      case "crew":
        parameter = "credit.crew";
        favourites = userPreference.getFavourite_directors();
        break;
      default:
        return new ArrayList<Credits>();
    }
    for (String choice : favourites) {
      queryString = queryString + addTypeChoice(getParameter(choice), parameter);
    }
    queryString = queryString.substring(0, queryString.length() - 3) + " order by title ASC";
    Query actorQuery = entityManager.createQuery(queryString, Credits.class);
    List<Credits> creditsOfActorChoice = actorQuery.getResultList();
    return creditsOfActorChoice;
  }

  /**
   * @param creditsOfActorChoice
   * @param creditsOfDirectorChoice
   * @return List of preferred movies
   */
  public JSONArray findPreferenceMovies(List<Credits> creditsOfActorChoice, List<Credits> creditsOfDirectorChoice) {
    ArrayList<String> movieList = new ArrayList<>();
    List<Credits> tempList = new ArrayList<>(creditsOfDirectorChoice);
    tempList.retainAll(creditsOfActorChoice); //Find the union of both the actors and directors
    for (int i = 0; i < 3 && i < tempList.size(); i++) {
      movieList.add(tempList.get(i).getTitle());//Fetch the 3 movies details.
    }
    //If union is of less than 3 movies, start adding from the actors preferred list
    if (movieList.size() < 3) {
      for (int i = 0; i < 3 && movieList.size() < 3; i++) {
        //If union is of less than 3 movies, start adding from the actors preferred list
        if (creditsOfActorChoice.size() > i + 1 && !tempList.contains(creditsOfActorChoice.get(i)))
          movieList.add(creditsOfActorChoice.get(i).getTitle());
      }
      if (tempList.size() < 3) {
        for (int i = 0; i < 3 && movieList.size() < 3; i++) {
          //If union is still less than 3 movies, start adding from the directors preferred list
          if (creditsOfDirectorChoice.size() > i + 1 && !tempList.contains(creditsOfDirectorChoice.get(i)))
            movieList.add(creditsOfActorChoice.get(i).getTitle());
        }
      }
    }
    //Sort the collection.
    Collections.sort(movieList);
    return new JSONArray(movieList);

  }

  private static String addTypeChoice(String choice, String type) {
    return " " + type + " like " + choice + " or ";
  }

  public static String getParameter(String input) {
    return " '%" + input.trim() + "%' ";
  }
}
