package com.project.whatflix;

import com.project.whatflix.services.RestServicesForUserTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    RestServicesForUserTest.class
})
@SpringBootTest

public class StartUpTest {

  @Test
  public void contextLoads() {

  }

}