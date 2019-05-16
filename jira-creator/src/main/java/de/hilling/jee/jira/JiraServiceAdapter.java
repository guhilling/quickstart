package de.hilling.jee.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import de.hilling.jee.jpa.ReceivedRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class JiraServiceAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(JiraServiceAdapter.class);

    @Inject
    private JiraServerConfiguration serverConfiguration;

    @Inject
    private JiraRestClient jiraRestClient;

    public void createIssue(ReceivedRequest request) {
        Long issueTypeId = serverConfiguration.issueIdForType(request.getType());
        final IssueInput issueInput = new IssueInputBuilder().setIssueTypeId(issueTypeId)
                                                             .setDescription(request.getDescription())
                                                             .setSummary(request.getSummary())
                                                             .setProjectKey(request.getProject())
                                                             .build();

        final BasicIssue issue = jiraRestClient.getIssueClient()
                                               .createIssue(issueInput)
                                               .claim();
        LOG.debug("created issue {}", issue);
    }
}
