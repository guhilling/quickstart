package de.hilling.jee.jpa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    public List<ReceivedRequest> failedRequests() {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ReceivedRequest> query = cb.createQuery(ReceivedRequest.class);

        final Root<ReceivedRequest> root = query.from(ReceivedRequest.class);

        query.where(cb.equal(root.get(ReceivedRequest_.state), RequestState.FAILED));

        return entityManager.createQuery(query).getResultList();
    }
}
