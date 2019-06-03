package de.hilling.jee.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class RepeatTimer {

    private static final Logger LOG = LoggerFactory.getLogger(RepeatTimer.class);

    @Schedule(persistent = false, minute = "*/2")
    public void repeatFailedRequests() {
        LOG.info("Called");
    }
}
