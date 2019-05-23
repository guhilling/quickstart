package de.hilling.jee.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import de.hilling.jee.jira.payment.Credit;
import de.hilling.jee.jira.payment.PaymentService;
import de.hilling.jee.jpa.ReceivedRequest;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.HashMap;

@ApplicationScoped
public class JiraServiceAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(JiraServiceAdapter.class);

    @ConfigProperty(name = "jira.uri", defaultValue = "http://localhost:8081")
    @Inject
    private String jiraUri;

    @ConfigProperty(name = "jira.username", defaultValue = "dummy")
    @Inject
    private String username;

    @ConfigProperty(name = "jira.password", defaultValue = "dummy")
    @Inject
    private String password;

    @Inject
    private Config configuration;

    @Inject
    private Instance<PaymentService> paymentService;

    private HashMap<String, Long> issueTypes = new HashMap<>();

    private static class DebitAnnotation extends AnnotationLiteral<Credit> implements Credit {
    }


    @PostConstruct
    public void init() {
        configuration.getPropertyNames().forEach(p -> LOG.info("property {}", p));
        paymentService.forEach(p -> LOG.info("found service {}", p));
        createClient().getMetadataClient()
                      .getIssueTypes()
                      .claim()
                      .forEach(i -> issueTypes.put(i.getName(), i.getId()));
    }

    public void createIssue(@NotNull @Valid ReceivedRequest request) {
        long issueTypeId = issueTypes.get(request.getType());
        try {
            Annotation creditAnnotation = new AnnotationLiteral<Credit>() {
            };
            paymentService.select(new DebitAnnotation()).get().pay(4);
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
                .createWithBasicHttpAuthentication(URI.create(jiraUri), username, password);
    }
}
