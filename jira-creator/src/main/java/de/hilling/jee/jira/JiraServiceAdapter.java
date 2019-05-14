package de.hilling.jee.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import de.hilling.jee.jpa.ReceivedRequest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.HashMap;

@ApplicationScoped
public class JiraServiceAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(JiraServiceAdapter.class);

    @ConfigProperty(name = "jira.uri")
    @Inject
    private URI jiraUri;

    @ConfigProperty(name = "jira.username")
    @Inject
    private String username;

    @ConfigProperty(name = "jira.password")
    @Inject
    private String password;

    private HashMap<String, Long> issueTypes = new HashMap<>();

    @PostConstruct
    public void init() {
        createClient().getMetadataClient()
                      .getIssueTypes()
                      .claim()
                      .forEach(i -> issueTypes.put(i.getName(), i.getId()));
    }

    public void createIssue(@NotNull @Valid ReceivedRequest request) {
        long issueTypeId = issueTypes.get(request.getType());
        try {
            final IssueInput issueInput = new IssueInputBuilder().setIssueTypeId(issueTypeId)
                                                                 .setDescription(request.getDescription())
                                                                 .setSummary(request.getSummary())
                                                                 .setProjectKey(request.getProject())
                                                                 .build();
            final BasicIssue issue = createClient().getIssueClient()
                                                   .createIssue(issueInput)
                                                   .claim();
            LOG.debug("created issue {}", issue);
        } catch (Exception e) {
            LOG.error("error", e);
        }
    }

    private JiraRestClient createClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(jiraUri, username, password);
    }
}
