package de.hilling.jee.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Caches data about server configuration like projects, issues etc.
 */
@ApplicationScoped
public class JiraServerConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(JiraServerConfiguration.class);

    private final HashMap<String, Long> issueTypes = new HashMap<>();

    private final AtomicBoolean initialized = new AtomicBoolean(false);

    private final JiraServerConnectionConfiguration connectionConfiguration;
    private final JiraRestClient restClient;

    @Inject
    public JiraServerConfiguration(JiraServerConnectionConfiguration connectionConfiguration, JiraRestClient restClient) {
        this.connectionConfiguration = connectionConfiguration;
        this.restClient = restClient;
    }

    protected JiraServerConfiguration() {
        this(null, null);
    }

    public synchronized void reload() {
        loadConfiguration();
    }

    private synchronized void assertInitialized() {
        if (!initialized.get()) {
            loadConfiguration();
        }
    }

    private void loadConfiguration() {
        try {
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


    public Long issueIdForType(String type) {
        assertInitialized();
        return issueTypes.get(type);
    }
}
