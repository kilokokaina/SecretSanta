package org.work.secretsantabot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.work.secretsantabot.model.Session;
import org.work.secretsantabot.repository.SessionRepository;
import org.work.secretsantabot.service.SessionService;
import org.work.secretsantabot.service.UserService;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final UserService userService;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    @Override
    public List<Session> findAllByUsername(String username) {
        return List.of();
    }

    @Override
    public Session findById(String sessionId) {
        return null;
    }

    @Override
    public boolean changeStatus(String sessionId, boolean status) {
        return false;
    }

    @Override
    public boolean joinSession(String sessionId) {
        return false;
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

}
