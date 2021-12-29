package com.datastax.killrvideo.it.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;

@Configuration
public class EtcdConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(EtcdConfiguration.class);

    @Inject
    private Environment env;


    @Bean
    public KillrVideoProperties getKillrViddeoProperties() {
        return new KillrVideoProperties(env);
    }
}