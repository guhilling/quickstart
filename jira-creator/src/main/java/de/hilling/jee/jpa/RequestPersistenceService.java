package de.hilling.jee.jpa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RequestPersistenceService {

    private static final Logger LOG = LoggerFactory.getLogger(RequestPersistenceService.class);

    @PersistenceContext
    private EntityManager entityManager;

    public long storeRequest(ReceivedRequest receivedRequest) {
        entityManager.persist(receivedRequest);
        LOG.info("stored request {}", receivedRequest);
        return receivedRequest.getId();
    }
}
