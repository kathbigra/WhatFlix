package com.project.whatflix.listeners;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class JobCompletionNotificationListenerForMovies extends JobExecutionListenerSupport {
  private final JdbcTemplate jdbcTemplate;
  Logger log = Logger.getLogger(JobCompletionNotificationListenerForCredits.class.getName());

  public JobCompletionNotificationListenerForMovies(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("Starting reading Movies data.");
    }
  }@Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("Finished reading Movies data.");
    }
  }
}
