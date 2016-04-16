package com.webapplication.authentication;

import com.webapplication.dto.user.SessionInfo;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Authenticator {

    private Map<UUID, SessionInfo> sessions = new ConcurrentHashMap<>();
    private final static int SESSION_TIME_OUT = 1000 * 60 * 60;
    public final static int SESSION_TIME_OUT_HOURS = SESSION_TIME_OUT / (1000 * 60 * 60);

    public UUID createSession(SessionInfo session) {
        UUID uuid = UUID.randomUUID();
        sessions.put(uuid, session);

        return uuid;
    }



    @Scheduled(fixedDelay = SESSION_TIME_OUT)
    private void cleanUpSessions() {
        sessions.entrySet().removeIf(session -> session.getValue().getDate().isAfter(DateTime.now()));
    }
}
