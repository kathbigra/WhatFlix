package com.project.whatflix.model;

import lombok.Data;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.Iterator;

@Data
public class UserPreference {
  private int id;
  private String[] preferred_languages;
  private String[] favourite_actors;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String[] getPreferred_languages() {
    return preferred_languages;
  }

  public void setPreferred_languages(String[] preferred_languages) {
    this.preferred_languages = preferred_languages;
  }

  public String[] getFavourite_actors() {
    return favourite_actors;
  }

  public void setFavourite_actors(String[] favourite_actors) {
    this.favourite_actors = favourite_actors;
  }

  public String[] getFavourite_directors() {
    return favourite_directors;
  }

  public void setFavourite_directors(String[] favourite_directors) {
    this.favourite_directors = favourite_directors;
  }

  private String[] favourite_directors;

  public static UserPreference fromJson(JSONObject userPreferenceJson) {
    UserPreference userPreference = new UserPreference();
    ((Iterator<String>) userPreferenceJson.keys()).forEachRemaining(s -> {
      try {
        JSONObject objecj = userPreferenceJson.getJSONObject(s);
        JSONArray fav = objecj.getJSONArray("favourite_actors");
        for (int i=0;i<fav.length();i++) {
          System.out.println(fav.getString(i));
        }
      } catch (Exception e) {
      }
    });

    return userPreference;
  }

}
