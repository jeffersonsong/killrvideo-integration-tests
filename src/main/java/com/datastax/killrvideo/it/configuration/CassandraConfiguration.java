package com.datastax.killrvideo.it.configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.killrvideo.it.util.ServiceChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static com.datastax.killrvideo.it.configuration.KillrVideoProperties.WAIT_TIME_IN_SECONDS;

@Configuration
public class CassandraConfiguration {

    private static final String CLUSTER_NAME = "killrvideo";
    private static final String KEYSPACE_NAME = "killrvideo";

    @Bean(destroyMethod = "close")
    public Session getSession(Environment env) throws Exception {
        final String address = env.getProperty("cassandra.host");
        final int port = Integer.parseInt(env.getProperty("cassandra.port"));

        ServiceChecker.waitForService("Cassandra", address, port, WAIT_TIME_IN_SECONDS);

        final Cluster cluster = Cluster
                .builder()
                .addContactPoint(address)
                .withPort(port)
                .withClusterName(CLUSTER_NAME)
                .build();

        return cluster.connect();
    }
}
