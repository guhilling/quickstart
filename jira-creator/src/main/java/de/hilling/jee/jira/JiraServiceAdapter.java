package de.hilling.jee.jira;

import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import de.hilling.jee.jpa.ReceivedRequest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class JiraServiceAdapter {

    @Inject
    private JiraServerConfiguration serverConfiguration;

    public void createIssue(ReceivedRequest request) {
        Long issueTypeId = 2L;
        final IssueInput issueInput = new IssueInputBuilder().setIssueTypeId(issueTypeId)
                                                             .setDescription(request.getDescription())
                                                             .setSummary(request.getSummary())
                                                             .setProjectKey(request.getProject())
                                                             .build();

        throw new RuntimeException("not yet implemented");
    }
}
