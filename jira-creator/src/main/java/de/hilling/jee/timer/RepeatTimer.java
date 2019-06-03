package de.hilling.jee.timer;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class RepeatTimer {

    @Schedule(persistent = false)
    public void repeatFailedRequests() {
    }
}
