package de.hilling.jee.jira;

import de.hilling.jee.jpa.ReceivedRequest;
import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.microprofile.ConfigPropertyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.inject.Inject;

@ExtendWith(CdiTestJunitExtension.class)
@ConfigPropertyValue(name = "jira.username", value = "gunnar@hilling.de")
@ConfigPropertyValue(name = "jira.uri", value = "https://hilling.atlassian.net")
class JiraServiceAdapterTest {

    @Inject
    private JiraServiceAdapter serviceAdapter;

    private ReceivedRequest request;

    @BeforeEach
    void setUp() {
        request = new ReceivedRequest();
        request.setType("Bug");
        request.setProject("RD");
        request.setDescription("Detaillierte Beschreibung.");
        request.setSummary("Überschrift");
    }

    @Test
    void createIssueOnOtherSystem() {
        serviceAdapter.createIssue(request);
    }

    @Test
    void describeIssueType() {
        serviceAdapter.describeType(request.getType());
    }
}