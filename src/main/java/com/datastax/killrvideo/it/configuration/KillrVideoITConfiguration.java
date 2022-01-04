package com.datastax.killrvideo.it.configuration;

import com.datastax.killrvideo.it.async.KillrVideoThreadFactory;
import com.datastax.killrvideo.it.util.ServiceChecker;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.datastax.killrvideo.it.configuration.KillrVideoProperties.WAIT_TIME_IN_SECONDS;


@Configuration
public class KillrVideoITConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(KillrVideoITConfiguration.class);

    public static final String USER_SERVICE_NAME = "UserManagementService";
    public static final String VIDEO_CATALOG_SERVICE_NAME = "VideoCatalogService";
    public static final String COMMENTS_SERVICE_NAME = "CommentsService";
    public static final String RATINGS_SERVICE_NAME = "RatingsService";
    public static final String STATISTICS_SERVICE_NAME = "StatisticsService";
    public static final String SEARCH_SERVICE_NAME = "SearchService";
    public static final String SUGGESTED_VIDEOS_SERVICE_NAME = "SuggestedVideoService";

    @Bean
    public ManagedChannel getManagedChannel(Environment env) throws Exception {
        final String address = env.getProperty("killrvideo.service.host");
        final int port = Integer.parseInt(env.getProperty("killrvideo.service.port"));

        ServiceChecker.waitForService("KillrVideoServer " + USER_SERVICE_NAME, address, port, WAIT_TIME_IN_SECONDS);

        System.out.println("Waiting 2 seconds for KillrVideoServer complete startup (conservative approach)");

        Thread.sleep(2000);

        return ManagedChannelBuilder
                .forAddress(address, port)
                .usePlaintext(true)
                .build();
    }

    @Bean(destroyMethod = "shutdownNow")
    public ExecutorService threadPool() {
        return new ThreadPoolExecutor(1, 10, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100), new KillrVideoThreadFactory());
    }

}
