package com.project.whatflix.jobs;

import com.project.whatflix.model.Movies;
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
public class ReadMoviesFromCSVJob {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private FlatFileItemReader<Movies> moviesFlatFileItemReader;

  @Autowired
  private JdbcBatchItemWriter<Movies> moviesJdbcBatchItemWriter;

  @Bean
  public Job readMoviesJob() {
    return jobBuilderFactory.get("readMoviesJob").incrementer(new RunIdIncrementer())
        .start(readMoviesStep())
        .build();
  }

  @Bean
  public Step readMoviesStep() {
    return stepBuilderFactory.get("readMoviesStep").<Movies, Movies>chunk(10).reader(moviesFlatFileItemReader)
        .writer(moviesJdbcBatchItemWriter).build();
  }

}
