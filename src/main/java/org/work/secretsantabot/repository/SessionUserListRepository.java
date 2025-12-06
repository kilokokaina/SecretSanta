package org.work.secretsantabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.work.secretsantabot.model.SessionUserList;

import java.util.List;

public interface SessionUserListRepository extends JpaRepository<SessionUserList, Long> {

    @Query(value = "SELECT session_id FROM session_user_list WHERE user_id = %:userId%", nativeQuery = true)
    List<String> findSessionIdByUserId(String userId);
    List<SessionUserList> findBySessionId(String sessionId);
    SessionUserList findByUserIdAndSessionId(String userId, String sessionId);

}
