package com.example.batch.config;

import com.example.batch.model.Product;
import com.example.batch.service.JsonReader;
import com.example.batch.service.ProductProcessor;
import com.example.batch.service.ProductWriter;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

// @Configuration
// @EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<JsonNode> itemReader() throws Exception {
        return new JsonReader("data/meta_Magazine_Subscriptions_100.json");
    }

    @Bean
    public ItemProcessor<JsonNode, Product> itemProcessor() {
        return new ProductProcessor();
    }

    @Bean
    public ItemWriter<Product> itemWriter() throws Exception {
        return new ProductWriter();
    }

    @Bean
    protected Step processProducts(ItemReader<JsonNode> reader, ItemProcessor<JsonNode, Product> processor,
            ItemWriter<Product> writer) {
        return stepBuilderFactory.get("processProducts").<JsonNode, Product>chunk(800)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor()).allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job chunksJob() throws Exception {
        return jobBuilderFactory
                .get("parseJob")
                .start(processProducts(itemReader(), itemProcessor(), itemWriter()))
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(32);
        executor.setQueueCapacity(40);
        return executor;
    }

}
