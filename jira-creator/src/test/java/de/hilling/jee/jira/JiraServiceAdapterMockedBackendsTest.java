package de.hilling.jee.jira;

import de.hilling.jee.jpa.ReceivedRequest;
import de.hilling.junit.cdi.CdiTestJunitExtension;
import de.hilling.junit.cdi.microprofile.ConfigPropertyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(CdiTestJunitExtension.class)
@ExtendWith(MockitoExtension.class)
@ConfigPropertyValue(name = "jira.username", value = "gunnar@hilling.de")
@ConfigPropertyValue(name = "jira.uri", value = "https://hilling.atlassian.net")
class JiraServiceAdapterMockedBackendsTest {

    @Inject
    private JiraServiceAdapter serviceAdapter;
    @Mock
    private JiraServerConnectionConfiguration connectionConfiguration;

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
        doThrow(new RuntimeException("test")).when(connectionConfiguration)
                                            .getJiraURI();
        assertThrows(RuntimeException.class,
                () -> serviceAdapter.createIssue(request),
                "test");

    }

}