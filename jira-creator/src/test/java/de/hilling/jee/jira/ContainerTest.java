package de.hilling.jee.jira;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
class ContainerTest {

    private static final Logger POSTGRESQL_LOG = LoggerFactory.getLogger("org.postgresql.server");

    @BeforeEach
    void setUpLog() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(POSTGRESQL_LOG);
        postgresqlContainer.followOutput(logConsumer);
    }

    @Container
    private static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer()
            .withDatabaseName("demo")
            .withUsername("gunnar")
            .withPassword("test");

    @Test
    void justAssert() {
        assertTrue(postgresqlContainer.isRunning());
    }

    @Test
    void useDatabase() throws SQLException {
        final String jdbcUrl = postgresqlContainer.getJdbcUrl();
        final String testQueryString = postgresqlContainer.getTestQueryString();
        Connection connection = DriverManager.getConnection(jdbcUrl, "gunnar", "test");
        connection.createStatement()
                  .executeQuery(testQueryString);
    }
}
