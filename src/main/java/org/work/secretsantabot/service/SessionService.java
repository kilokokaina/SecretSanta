package org.work.secretsantabot.service;

import org.work.secretsantabot.model.Session;
import org.work.secretsantabot.model.SessionUserList;
import org.work.secretsantabot.model.User;

import java.util.List;

public interface SessionService {

    List<Session> findAllByUsername(String username);
    List<Session> findAllByUserId(String userId);
    List<User> findAllUsersBySessionId(String sessionId);
    Session findById(String sessionId);
    boolean changeStatus(String sessionId, boolean status);
    Session joinSession(SessionUserList sessionUserList);
    Session save(Session session);
    List<SessionUserList> getSessionUserList(String sessionId);

}
