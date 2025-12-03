package org.work.secretsantabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.secretsantabot.model.Session;

public interface SessionRepository extends JpaRepository<Session, String> {
}
