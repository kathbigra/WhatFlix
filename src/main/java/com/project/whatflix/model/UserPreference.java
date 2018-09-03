package com.project.whatflix.model;

import lombok.Data;

import java.util.List;

@Data
public class UserPreference {
  private List<String> preferred_languages;
  private List<String> favourite_actors;
  private List<String> favourite_directors;

  public List<String> getPreferred_languages() {
    return preferred_languages;
  }

  public void setPreferred_languages(List<String> preferred_languages) {
    this.preferred_languages = preferred_languages;
  }

  public List<String> getFavourite_actors() {
    return favourite_actors;
  }

  public void setFavourite_actors(List<String> favourite_actors) {
    this.favourite_actors = favourite_actors;
  }

  public List<String> getFavourite_directors() {
    return favourite_directors;
  }

  public void setFavourite_directors(List<String> favourite_directors) {
    this.favourite_directors = favourite_directors;
  }


}
