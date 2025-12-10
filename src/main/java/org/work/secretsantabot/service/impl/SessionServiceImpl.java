package org.work.secretsantabot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.work.secretsantabot.model.Session;
import org.work.secretsantabot.model.SessionUserList;
import org.work.secretsantabot.model.User;
import org.work.secretsantabot.repository.SessionRepository;
import org.work.secretsantabot.repository.SessionUserListRepository;
import org.work.secretsantabot.service.SessionService;
import org.work.secretsantabot.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionUserListRepository sulRepository;
    private final UserService userService;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, SessionUserListRepository sulRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.sulRepository = sulRepository;
        this.userService = userService;
    }

    @Override
    public List<Session> findAllByUsername(String username) {
        return sessionRepository.findByAdminUserId(username);
    }

    @Override
    public List<Session> findAllByUserId(String userId) {
        var sessionIds = sulRepository.findSessionIdByUserId(userId);
        var sessions = new ArrayList<Session>();

        for (var sessionId : sessionIds) sessions.add(sessionRepository.findBySessionId(sessionId));

        return sessions;
    }

    @Override
    public List<SessionUserList> getSessionUserList(String sessionId) {
        return sulRepository.findBySessionId(sessionId);
    }

    @Override
    public SessionUserList findByUserIdAndSessionId(String userId, String sessionId) {
        return sulRepository.findByUserIdAndSessionId(userId, sessionId);
    }

    @Override
    public Session findById(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    @Override
    public Session joinSession(SessionUserList sessionUserList) {
        sulRepository.save(sessionUserList);
        return sessionRepository.findById(sessionUserList.getSessionId()).orElse(null);
    }

    @Override
    public SessionUserList updateSessionUserList(SessionUserList sessionUserList) {
        return sulRepository.save(sessionUserList);
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public void deleteSessionUserList(String userId, String sessionId) {
        var userForm = sulRepository.findByUserIdAndSessionId(userId, sessionId);
        sulRepository.delete(userForm);
    }

    @Override
    public void startSending(String sessionId) {
        var users = sulRepository.findUserBySessionId(sessionId);
        log.info("Users: {}", users);


    }

}
