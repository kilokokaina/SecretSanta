package org.work.secretsantabot.service.impl;

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

        for (var sessionId : sessionIds) {
            sessions.add(sessionRepository.findBySessionId(sessionId));
        }

        return sessions;
    }

    @Override
    public List<User> findAllUsersBySessionId(String sessionId) {
        var userIds = sulRepository.findUserIdBySessionId(sessionId);
        var users = new ArrayList<User>();

        for (var userId : userIds) {
            users.add(userService.findByUserId(userId));
        }

        return users;
    }

    @Override
    public Session findById(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    @Override
    public boolean changeStatus(String sessionId, boolean status) {
        return false;
    }

    @Override
    public Session joinSession(SessionUserList sessionUserList) {
        sulRepository.save(sessionUserList);
        return sessionRepository.findById(sessionUserList.getSessionId()).orElse(null);
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

}
