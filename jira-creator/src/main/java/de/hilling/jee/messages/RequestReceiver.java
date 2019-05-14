package de.hilling.jee.messages;

import de.hilling.jee.jira.JiraServiceAdapter;
import de.hilling.jee.jpa.ReceivedRequest;
import de.hilling.jee.jpa.RequestPersistenceService;
import de.hilling.jee.json.JsonRequestParser;
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
    @Inject
    private JsonRequestParser parser;

    @Override
    public void onMessage(Message message) {
        LOG.debug("forwarding message {}", message);

        final ReceivedRequest request;
        try {
            request = parser.parse(message.getBody(String.class));
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
