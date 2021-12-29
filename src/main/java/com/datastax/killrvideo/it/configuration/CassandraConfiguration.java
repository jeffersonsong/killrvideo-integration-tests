package com.datastax.killrvideo.it.configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.killrvideo.it.util.HostAndPortSplitter;
import com.datastax.killrvideo.it.util.ServiceChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.datastax.killrvideo.it.configuration.KillrVideoProperties.WAIT_TIME_IN_SECONDS;
import static java.lang.String.format;

@Configuration
public class CassandraConfiguration {

    private static final String CLUSTER_NAME = "killrvideo";
    private static final String KEYSPACE_NAME = "killrvideo";

    @Bean(destroyMethod = "close")
    public Session getSession() throws Exception {
        final String hostAndPort = "127.0.0.1:9042";
        assert hostAndPort != null;
        HostAndPortSplitter.ensureValidFormat(hostAndPort,
                format("The %s is not a valid host:port format", hostAndPort));

        final String address = HostAndPortSplitter.extractAddress(hostAndPort);
        final int port = HostAndPortSplitter.extractPort(hostAndPort);

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
