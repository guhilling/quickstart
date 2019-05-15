package de.hilling.jee.jira;

import de.hilling.jee.jpa.ReceivedRequest;
import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.microprofile.ConfigPropertyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@ExtendWith(CdiTestJunitExtension.class)
@ConfigPropertyValue(name = "jira.username", value = "tester")
@ConfigPropertyValue(name = "jira.password", value = "tester1")
class JiraServiceAdapterTest {

    @Inject
    private JiraServiceAdapter serviceAdapter;

    private ReceivedRequest request;

    @BeforeEach
    void setUp() {
        request = new ReceivedRequest();
    }

    @Test
    void createIssue() {
        serviceAdapter.createIssue(request);
    }

    @Test
    @ConfigPropertyValue(name = "jira.uri", value = "https://hilling.atlassian.net")
    public void createIssueOnOtherSystem() {
        serviceAdapter.createIssue(request);
    }
}