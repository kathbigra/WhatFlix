package com.project.whatflix.jobs;

import com.project.whatflix.model.Credits;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReadCredotsFromCSVJob {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private FlatFileItemReader<Credits> creditsFlatFileItemReader;

  @Autowired
  private JdbcBatchItemWriter<Credits> creditsJdbcBatchItemWriter;

  @Bean
  public Job readCreditsJob() {
    return jobBuilderFactory.get("readCreditsJob").incrementer(new RunIdIncrementer())
        .start(readCreditsStep()).build();
  }

  @Bean
  public Step readCreditsStep() {
    return stepBuilderFactory.get("readCreditsStep").<Credits, Credits>chunk(10)
        .reader(creditsFlatFileItemReader).writer(creditsJdbcBatchItemWriter).build();
  }
}
