package de.hilling.jee.rest;

import de.hilling.jee.jira.JiraServiceAdapter;
import de.hilling.jee.jpa.ReceivedRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.time.LocalDateTime;

@Path("/")
@ApplicationScoped
public class RestCreator {

    @Inject
    private JiraServiceAdapter serviceAdapter;

    @POST
    public void createIssue() {
        final ReceivedRequest request = new ReceivedRequest();
        request.setSummary("summary");
        request.setDescription("description");
        request.setProject("RD");
        request.setType("Bug");
        request.setRequestedAt(LocalDateTime.now());
        serviceAdapter.createIssue(request);
    }
}
