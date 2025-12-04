package org.work.secretsantabot.service;

import org.work.secretsantabot.model.Session;

import java.util.List;

public interface SessionService {

    List<Session> findAllByUsername(String username);
    Session findById(String sessionId);
    boolean changeStatus(String sessionId, boolean status);
    boolean joinSession(String sessionId);
    Session save(Session session);

}
