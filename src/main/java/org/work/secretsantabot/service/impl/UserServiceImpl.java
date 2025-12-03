package org.work.secretsantabot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.work.secretsantabot.model.User;
import org.work.secretsantabot.repository.UserRepository;
import org.work.secretsantabot.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByTelegramUsername(username);
    }

    @Override
    public User findByAuthToken(String authToken) {
        return userRepository.findByAuthToken(authToken);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

}
