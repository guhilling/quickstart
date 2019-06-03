package de.hilling.jee.jira;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Configuration for JIRA server connection.
 */
@ApplicationScoped
public class JiraServerConnectionConfiguration {

    public String getJiraURI() {
        return jiraURI;
    }

    public String getJiraUsername() {
        return jiraUsername;
    }

    public String getJiraPassword() {
        return jiraPassword;
    }

    @Inject
    @ConfigProperty(name = "jira.uri", defaultValue = "http://localhost:8081")
    private String jiraURI;
    @Inject
    @ConfigProperty(name = "jira.username", defaultValue = "")
    private String jiraUsername;
    @Inject
    @ConfigProperty(name = "jira.password", defaultValue = "")
    private String jiraPassword;

}
