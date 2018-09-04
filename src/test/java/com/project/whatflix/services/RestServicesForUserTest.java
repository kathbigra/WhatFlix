package com.project.whatflix.services;

import com.project.whatflix.AppConfig;
import com.project.whatflix.controller.RESTServiceController;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestServicesForUserTest {
  MockMvc mockMvc;

  @Mock
  private RestServicesForUser restServicesForUser;
  @Autowired
  private TestRestTemplate template;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(restServicesForUser).build();
  }

  @Autowired
  RESTServiceController userPreferenceConfiguration;

  @Test
  public void testgetMoviesForUserByuserId() {
    String userid = userPreferenceConfiguration.getUserPreferenceData().entrySet().iterator().next().getKey();
    String queryString = "Brad Pitt,Will Smith";
    String url = "/movies/user/" + userid + "/search/?text=" + queryString;
    ResponseEntity<String> response = template.getForEntity(url, String.class);
    Assert.assertEquals(response.getStatusCode(), (HttpStatus.OK));
    try {
      JSONArray ja = new JSONArray(response.getBody());
      Assert.assertTrue(ja.length() > 0);
    } catch (JSONException e) {
      Assert.assertTrue(false);
    }
  }

  @Test
  public void testgetMoviesForUserForWronuserId() {
    String userid = "-1";
    String queryString = "Brad Pitt,Will Smith";
    String url = "/movies/user/" + userid + "/search/?text=" + queryString;
    ResponseEntity<String> response = template.getForEntity(url, String.class);
    Assert.assertEquals(response.getStatusCode(), (HttpStatus.OK));
    try {
      JSONArray ja = new JSONArray(response.getBody());
      Assert.assertTrue(ja.length() == 0);
    } catch (JSONException e) {
      Assert.assertTrue(false);
    }
  }

  @Test
  public void testgetMoviesForUserForWronQuery() {
    String userid = userPreferenceConfiguration.getUserPreferenceData().entrySet().iterator().next().getKey();
    String queryString = "Humpty Dumpty Dummy String Test To fail";
    String url = "/movies/user/" + userid + "/search/?text=" + queryString;
    ResponseEntity<String> response = template.getForEntity(url, String.class);
    Assert.assertEquals(response.getStatusCode(), (HttpStatus.OK));
    try {
      JSONArray ja = new JSONArray(response.getBody());
      Assert.assertTrue(ja.length() == 0);
    } catch (JSONException e) {
      Assert.assertTrue(false);
    }
  }


  @Test
  public void testgetMoviesForUsersBasedOnPreference() {
    String url = "/movies/users";
    ResponseEntity<String> response = template.getForEntity(url, String.class);
    Assert.assertEquals(response.getStatusCode(), (HttpStatus.OK));
    try {
      JSONArray ja = new JSONArray(response.getBody());
      Assert.assertEquals(ja.length(), userPreferenceConfiguration.getUserPreferenceData().size());
      for (int i = 0; i < ja.length(); i++) {
        Assert.assertEquals(ja.getJSONObject(i).getJSONArray("movies").length(), 3);
      }
    } catch (JSONException e) {
      Assert.assertTrue(false);
    }
  }

  @Test
  public void testgetMoviesForUsersBasedOnPreferenceForExact3MoviesPerUsere() {
    String url = "/movies/users";
    ResponseEntity<String> response = template.getForEntity(url, String.class);
    Assert.assertEquals(response.getStatusCode(), (HttpStatus.OK));
    try {
      JSONArray ja = new JSONArray(response.getBody());
      Assert.assertEquals(ja.length(), userPreferenceConfiguration.getUserPreferenceData().size());

    } catch (JSONException e) {
      Assert.assertTrue(false);
    }

  }


}
