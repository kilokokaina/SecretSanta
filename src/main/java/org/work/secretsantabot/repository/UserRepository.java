package org.work.secretsantabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.secretsantabot.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByTelegramUsername(String username);
    User findByAuthToken(String authToken);

}
