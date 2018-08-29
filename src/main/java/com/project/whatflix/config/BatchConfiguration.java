package com.project.whatflix.config;

import com.project.whatflix.model.Credits;
import com.project.whatflix.model.Movies;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
  Logger log = Logger.getLogger(BatchConfiguration.class.getName());
  @Autowired
  private DataSource dataSource;

  @Bean
  public FlatFileItemReader<Credits> readerCredit() {
    FlatFileItemReader<Credits> reader = new FlatFileItemReader<>();
    reader.setResource(new ClassPathResource("tmdb_5000_credits.csv"));
    reader.setLineMapper(new DefaultLineMapper<Credits>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames(new String[]{"movie_id", "title", "cast", "crew"});
      }});
      setFieldSetMapper(new BeanWrapperFieldSetMapper<Credits>() {{
        setTargetType(Credits.class);
      }});
    }});
    return reader;
  }

  @Bean
  public FlatFileItemReader<Movies> readerMovies() {
    FlatFileItemReader<Movies> reader = new FlatFileItemReader<>();
    reader.setResource(new ClassPathResource("tmdb_5000_movies.csv"));
    reader.setLineMapper(new DefaultLineMapper<Movies>() {{
      setLineTokenizer(new DelimitedLineTokenizer() {{
        setNames(new String[]{
            "budget", "genres", "homepage", "id", "keywords", "original_language", "original_title", "overview",
            "popularity", "production_companies", "production_countries", "release_date", "revenue", "runtime",
            "spoken_languages", "status", "tagline", "title", "vote_average", "vote_count"
        });
      }});
      setFieldSetMapper(new BeanWrapperFieldSetMapper<Movies>() {{
        setTargetType(Movies.class);
      }});
    }});
    return reader;
  }


  @Bean
  public JdbcBatchItemWriter<Credits> writerCredits() {
    JdbcBatchItemWriter<Credits> writer = new JdbcBatchItemWriter<>();
    writer.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<Credits>());
    writer.setSql("INSERT INTO credits (movie_id,title,cast,crew) VALUES (:movie_id,:title,:cast,:crew)");
    writer.setDataSource(dataSource);
    return writer;
  }

  @Bean
  public JdbcBatchItemWriter<Movies> writerMovies() {
    JdbcBatchItemWriter<Movies> writer = new JdbcBatchItemWriter<>();
    writer.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<Movies>());
    writer.setSql("INSERT INTO movies (budget,genres,homepage,id,keywords,original_language,original_title,overview,popularity,production_companies,production_countries,release_date,revenue,runtime,spoken_languages,status,tagline,title,vote_average,vote_count) VALUES (:budget,:genres,:homepage,:id,:keywords,:original_language,:original_title,:overview,popularity,:production_companies,:production_countries,:release_date,:revenue,:runtime,:spoken_languages,:status,:tagline,:title,:vote_average,:vote_count)");
    writer.setDataSource(dataSource);
    return writer;
  }
}
