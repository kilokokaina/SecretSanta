package org.work.secretsantabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.work.secretsantabot.model.SessionUserList;
import org.work.secretsantabot.model.User;

import java.util.List;

public interface SessionUserListRepository extends JpaRepository<SessionUserList, Long> {

    @Query(value = "SELECT session_id FROM session_user_list WHERE user_id = %:userId%", nativeQuery = true)
    List<String> findSessionIdByUserId(String userId);
    @Query(value = "SELECT u.* FROM session_user_list AS sul LEFT JOIN user AS u ON sul.user_id = u.user_id WHERE sul.session_id = %:sessionId%", nativeQuery = true)
    List<User> findUserBySessionId(String sessionId);
    List<SessionUserList> findBySessionId(String sessionId);
    SessionUserList findByUserIdAndSessionId(String userId, String sessionId);

}
