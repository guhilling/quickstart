package de.hilling.jee.timer;

import de.hilling.jee.jira.JiraServiceAdapter;
import de.hilling.jee.jpa.RequestPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.Collection;

@Singleton
@Startup
public class RepeatTimer {
    private static final Logger LOG = LoggerFactory.getLogger(RepeatTimer.class);

    @Resource
    private TimerService timerService;

    @Inject
    private JiraServiceAdapter jiraServiceAdapter;

    @Inject
    private RequestPersistenceService requestPersistenceService;

    @PostConstruct
    void init() {
        final Collection<Timer> timers = timerService.getTimers();
        for(Timer timer: timers) {
            LOG.info("found timer: {}", timer);
        }
    }

    @Schedule(persistent = false, second = "*/30", minute = "*", hour = "*")
    public void repeatFailedRequests(Timer timer) {
        LOG.info("called by timer {}", timer);
        requestPersistenceService.failedRequests()
                                 .forEach(r -> LOG.info("request {}", r));
        throw new RuntimeException("test");
    }
}
