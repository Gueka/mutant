package com.gueka.mutant.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Profile("test")
@EnableReactiveMongoRepositories(basePackages= {"com.gueka.mutant.repository"})
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@ComponentScan(basePackages = {
        "com.gueka.mutant.config",
        "com.gueka.mutant.controller",
        "com.gueka.mutant.service",
        "com.gueka.mutant.request",
        "com.gueka.mutant.domain"
        })
@AutoConfigureAfter(EmbeddedMongoAutoConfiguration.class)
public class DBTestConfig extends AbstractReactiveMongoConfiguration {
	
    private final Environment environment;
    
    public DBTestConfig(Environment environment) {
        this.environment = environment;
    }
    
    @Override
    @Bean
    @DependsOn("embeddedMongoServer")
    public MongoClient reactiveMongoClient() {
        int port = environment.getProperty("local.mongo.port", Integer.class);
        return MongoClients.create(String.format("mongodb://localhost:%d", port));
    }
    
    @Override
    protected String getDatabaseName() {
        return "test";
    }
}
