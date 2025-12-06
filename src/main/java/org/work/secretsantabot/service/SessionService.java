package org.work.secretsantabot.service;

import org.work.secretsantabot.model.Session;
import org.work.secretsantabot.model.SessionUserList;

import java.util.List;

public interface SessionService {

    List<Session> findAllByUsername(String username);
    List<Session> findAllByUserId(String userId);
    Session findById(String sessionId);
    boolean changeStatus(String sessionId, boolean status);
    Session joinSession(SessionUserList sessionUserList);
    Session save(Session session);
    List<SessionUserList> getSessionUserList(String sessionId);
    SessionUserList findByUserIdAndSessionId(String userId, String sessionId);

}
