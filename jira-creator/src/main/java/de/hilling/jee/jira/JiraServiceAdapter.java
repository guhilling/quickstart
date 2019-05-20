package de.hilling.jee.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import de.hilling.jee.jpa.ReceivedRequest;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
@Metered(absolute = true)
public class JiraServiceAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(JiraServiceAdapter.class);

    private final JiraServerConfiguration serverConfiguration;
    private final JiraRestClient jiraRestClient;

    @Inject
    @Metric(description = "No of requested JIRA Issues",
            displayName = "JIRARequestCount")
    private Counter requestCount;

    @Inject
    @Metric(description = "No of errors when creating JIRA Issues",
            displayName = "JIRAErrorCount")
    private Counter jiraErrorCount;

    @Inject
    public JiraServiceAdapter(JiraServerConfiguration serverConfiguration, JiraRestClient jiraRestClient) {
        this.serverConfiguration = serverConfiguration;
        this.jiraRestClient = jiraRestClient;
    }

    protected JiraServiceAdapter() {
        this(null, null);
    }

    public void createIssue(ReceivedRequest request) {
        requestCount.inc();
        try {
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
        } catch (RuntimeException re) {
            jiraErrorCount.inc();
            LOG.error("creating issue from request {} failed", request, re);
            throw re;
        }
    }

    public void describeType(String type) {
        try {
            jiraRestClient.getMetadataClient()
                          .getIssueTypes()
                          .claim()
                          .forEach(t -> LOG.debug("type {}", t));
        } catch (RuntimeException re) {
            LOG.error("finding type  {} failed", type, re);
            throw re;
        }
    }
}
