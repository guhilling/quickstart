package de.hilling.jee.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;

public class JiraClientFactory {

    private static final Logger LOG = LoggerFactory.getLogger(JiraClientFactory.class);

    @Inject
    private JiraServerConnectionConfiguration connectionConfiguration;

    @Produces
    @RequestScoped
    JiraRestClient provideClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(URI.create(connectionConfiguration.getJiraURI()),
                        connectionConfiguration.getJiraUsername(),
                        connectionConfiguration.getJiraPassword());
    }

    void disposeJiraClient(@Disposes JiraRestClient client) {
        try {
            client.close();
        } catch (IOException e) {
            LOG.error("closing jira client", e);
        }
    }


}
