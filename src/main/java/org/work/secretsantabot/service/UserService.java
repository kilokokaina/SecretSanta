package org.work.secretsantabot.service;

import org.work.secretsantabot.model.User;

public interface UserService {

    User findByUsername(String username);
    User findByAuthToken(String authToken);
    void save(User user);

}
