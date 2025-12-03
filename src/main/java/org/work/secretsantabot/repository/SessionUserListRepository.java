package org.work.secretsantabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.secretsantabot.model.SessionUserList;

public interface SessionUserListRepository extends JpaRepository<SessionUserList, Long> {
}
