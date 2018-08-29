package com.project.whatflix.model;

import lombok.Data;

@Data
public class Credits {
  public String getMovie_id() {
    return movie_id;
  }

  public void setMovie_id(String movie_id) {
    this.movie_id = movie_id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCast() {
    return cast;
  }

  public void setCast(String cast) {
    this.cast = cast;
  }

  public String getCrew() {
    return crew;
  }

  public void setCrew(String crew) {
    this.crew = crew;
  }

  private String movie_id;
  private String title;
  private String cast;
  private String crew;

  public Credits(String movie_id, String title, String cast, String crew) {
    this.movie_id = movie_id;
    this.cast = cast;
    this.title = title;
    this.crew = crew;
  }

  public Credits() {
  }
}
