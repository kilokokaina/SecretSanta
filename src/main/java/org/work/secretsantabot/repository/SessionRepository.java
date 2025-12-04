package org.work.secretsantabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.secretsantabot.model.Session;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, String> {

    List<Session> findBySessionId(String sessionId);

}
