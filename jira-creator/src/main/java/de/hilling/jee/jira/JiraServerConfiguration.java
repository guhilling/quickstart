package de.hilling.jee.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Cached Daten über Server-Configuration wie IssueTypes, Projekte, etc.
 */
@ApplicationScoped
public class JiraServerConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(JiraServerConfiguration.class);

    private HashMap<String, Long> issueTypes = new HashMap<>();

    private AtomicBoolean initialized = new AtomicBoolean(false);

    private synchronized void assertInitialized() {
        if (!initialized.get()) {
            try (JiraRestClient restClient = createClient()) {
                restClient.getMetadataClient()
                          .getIssueTypes()
                          .claim()
                          .forEach(it -> issueTypes.put(it.getName(), it.getId()));
                initialized.set(true);
            } catch (Exception e) {
                LOG.error("error initializing server config", e);
                throw new RuntimeException("server config not found");
            }
        }
    }


    @Produces
    @RequestScoped
    public JiraRestClient provideClient() {
        assertInitialized();
        return createClient();
    }

    private JiraRestClient createClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(URI.create("https://test-jira.isios.com/test-jira"),
                        "test",
                        "test");
    }

    public void disposeJiraClient(@Disposes JiraRestClient client) {
        try {
            client.close();
        } catch (IOException e) {
            LOG.error("closing jira client", e);
        }
    }

    public Long issueIdForType(String type) {
        assertInitialized();
        return issueTypes.get(type);
    }
}
