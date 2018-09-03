package com.project.whatflix.listeners;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class JobCompletionNotificationListenerForCredits extends JobExecutionListenerSupport {
  private final JdbcTemplate jdbcTemplate;
  Logger log = Logger.getLogger(JobCompletionNotificationListenerForCredits.class.getName());

  public JobCompletionNotificationListenerForCredits(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("Starting reading Credits data.");
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("Finished reading Credits data.");
    }
  }
}
