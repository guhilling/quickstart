package de.hilling.jee.json;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.junit.jupiter.api.Test;

import java.net.URI;

class JiraLibraryTest {

    @Test
    void callServer() {
        final JiraRestClient restClient = new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(URI.create("https://test-jira.isios.com/test-jira"),
                        "test",
                        "test");
        restClient.getMetadataClient()
                  .getIssueTypes()
                  .claim()
                  .forEach(it -> System.out.println("found " + it));
        restClient.getIssueClient();
        restClient.getProjectClient();
    }

}
