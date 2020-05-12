package com.balljames.batch.batchconfig;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.balljames.batch.processor.GameDataProcessor;
import com.balljames.model.GameData;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	 public JobBuilderFactory jobBuilderFactory;
	 
	 @Autowired
	 public StepBuilderFactory stepBuilderFactory;
	 
	 @Autowired
	 public DataSource dataSource;
	 
	 @Bean
	 public FlatFileItemReader<GameData> reader(){
	  FlatFileItemReader<GameData> reader = new FlatFileItemReader<GameData>();
	  reader.setResource(new ClassPathResource(ConfigConstants.FILE_PATH));
	  reader.setLineMapper(new DefaultLineMapper<GameData>() {{
	    setLineTokenizer(new CustomRegexLineTokenizer() {{
		setRegex(ConfigConstants.REGEX_FORMAT);
	    setNames(ConfigConstants.TABLE_NAMES);
	   }});
	   setFieldSetMapper(new BeanWrapperFieldSetMapper<GameData>() {{
	    setTargetType(GameData.class);
	   }});
	   
	  }});
	  
	  return reader;
	 }
	 
	 @Bean
	 public GameDataProcessor processor(){
	  return new GameDataProcessor();
	 }
	 
	 @Bean
	 public JdbcBatchItemWriter<GameData> writer(){
	  JdbcBatchItemWriter<GameData> writer = new JdbcBatchItemWriter<GameData>();
	  writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<GameData>());
	  writer.setSql("INSERT INTO gameData (" + ConfigConstants.SQL_TABLENAMES + ") VALUES (" + ConfigConstants.SQL_VALUES + ")");
	  writer.setDataSource(dataSource);
	  
	  return writer;
	 }
	 
	 @Bean
	 public Step step1() {
	  return stepBuilderFactory.get("step1").<GameData, GameData> chunk(25)
	    .reader(reader())
	    .processor(processor())
	    .writer(writer())
	    .build();
	 }
	 
	 @Bean
	 public Job importUserJob() {
	  return jobBuilderFactory.get("importUserJob")
	    .incrementer(new RunIdIncrementer())
	    .flow(step1())
	    .end()
	    .build();
	 }
}
