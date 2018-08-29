package com.project.whatflix.model;

import lombok.Data;

@Data
public class Movies {
  public Movies(String budget, String genres, String homepage, String id, String keywords, String original_language, String original_title, String overview, String popularity, String production_companies, String production_countries, String release_date, String revenue, String runtime, String spoken_languages, String status, String tagline, String title, String vote_average, String vote_count) {
    this.budget = budget;
    this.genres = genres;
    this.homepage = homepage;
    this.id = id;
    this.keywords = keywords;
    this.original_language = original_language;
    this.original_title = original_title;
    this.overview = overview;
    this.popularity = popularity;
    this.production_companies = production_companies;
    this.production_countries = production_countries;
    this.release_date = release_date;
    this.revenue = revenue;
    this.runtime = runtime;
    this.spoken_languages = spoken_languages;
    this.status = status;
    this.tagline = tagline;
    this.title = title;
    this.vote_average = vote_average;
    this.vote_count = vote_count;
  }

  public Movies(){
  }

  public String getBudget() {
    return budget;
  }

  public void setBudget(String budget) {
    this.budget = budget;
  }

  public String getGenres() {
    return genres;
  }

  public void setGenres(String genres) {
    this.genres = genres;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getOriginal_language() {
    return original_language;
  }

  public void setOriginal_language(String original_language) {
    this.original_language = original_language;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public void setOriginal_title(String original_title) {
    this.original_title = original_title;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getPopularity() {
    return popularity;
  }

  public void setPopularity(String popularity) {
    this.popularity = popularity;
  }

  public String getProduction_companies() {
    return production_companies;
  }

  public void setProduction_companies(String production_companies) {
    this.production_companies = production_companies;
  }

  public String getProduction_countries() {
    return production_countries;
  }

  public void setProduction_countries(String production_countries) {
    this.production_countries = production_countries;
  }

  public String getRelease_date() {
    return release_date;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }

  public String getRevenue() {
    return revenue;
  }

  public void setRevenue(String revenue) {
    this.revenue = revenue;
  }

  public String getRuntime() {
    return runtime;
  }

  public void setRuntime(String runtime) {
    this.runtime = runtime;
  }

  public String getSpoken_languages() {
    return spoken_languages;
  }

  public void setSpoken_languages(String spoken_languages) {
    this.spoken_languages = spoken_languages;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTagline() {
    return tagline;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getVote_average() {
    return vote_average;
  }

  public void setVote_average(String vote_average) {
    this.vote_average = vote_average;
  }

  public String getVote_count() {
    return vote_count;
  }

  public void setVote_count(String vote_count) {
    this.vote_count = vote_count;
  }

  private String budget, genres, homepage, id, keywords, original_language, original_title, overview, popularity, production_companies, production_countries, release_date, revenue, runtime, spoken_languages, status, tagline, title, vote_average, vote_count;

}
