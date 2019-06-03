package de.hilling.jee.json;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class JiraRestClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(JiraRestClientTest.class);

    private URI uri = URI.create("https://hilling.atlassian.net/");

    @Test
    void queryAllProjects() {
        final JiraRestClient jiraClient = new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(uri, "gunnar@hilling.de", "CCaBsfvcRH6ov8mudHiG6EFA");
        final Iterable<BasicProject> projects = jiraClient.getProjectClient()
                                                       .getAllProjects()
                                                       .claim();
        Assertions.assertNotNull(projects);
    }
}
