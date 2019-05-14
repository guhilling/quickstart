package de.hilling.jee.messages;

import de.hilling.jee.jira.JiraServiceAdapter;
import de.hilling.jee.jpa.ReceivedRequest;
import de.hilling.jee.jpa.RequestPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/jira")
        }
)
public class RequestReceiver implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(RequestReceiver.class);
    @Inject
    RequestPersistenceService requestPersistenceService;
    @Inject
    private JiraServiceAdapter serviceAdapter;

    @Override
    public void onMessage(Message message) {
        LOG.debug("forwarding message {}", message);
        final ReceivedRequest request = new ReceivedRequest();
        try {
            request.setContent(message.getBody(String.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            serviceAdapter.createIssue(request);
            LOG.debug("created issue {}", message);
        } catch (RuntimeException e) {
            requestPersistenceService.storeRequest(request);
            LOG.error("failed on message {} (will retry)", message);
        }
    }
}
