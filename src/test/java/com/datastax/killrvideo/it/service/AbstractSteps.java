package com.datastax.killrvideo.it.service;

import com.datastax.killrvideo.it.configuration.KillrVideoProperties;
import com.datastax.killrvideo.it.dao.CassandraDao;
import cucumber.api.PendingException;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;

import static java.lang.String.format;

@ContextConfiguration
@SpringBootTest
public abstract class AbstractSteps {

    /** Logger for Test.*/
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractSteps.class);
    
    @Inject
    protected ManagedChannel managedChannel;

    @Inject
    CassandraDao dao;

    @Inject
    ExecutorService threadPool;

    @Inject
    KillrVideoProperties properties;

    protected abstract String serviceName();

    protected void checkForService() {

        try {
            LOGGER.info("Testing service {} in ETCD OK", serviceName());
        } catch (Exception e) {
            throw new PendingException(format("Please implement service %s on the KillrVideoServer",serviceName()));
        }

    }
}
