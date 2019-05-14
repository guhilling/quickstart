package de.hilling.jee.jira;

import de.hilling.jee.jpa.ReceivedRequest;

import javax.ejb.Stateless;

@Stateless
public class JiraServiceAdapter {
    public void createIssue(ReceivedRequest request) {
        throw new RuntimeException("not yet implemented");
    }
}
