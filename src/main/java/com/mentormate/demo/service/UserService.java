package com.mentormate.demo.service;

import com.mentormate.demo.entity.User;
import com.mentormate.demo.exception.UserNotFoundException;
import com.mentormate.demo.repository.UserRepository;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with id: " + id + "not found."));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updateInfo) {
        User user = getUserById(id);

        user.setEmail(updateInfo.getEmail());
        user.setFirstName(updateInfo.getFirstName());
        user.setLastName(updateInfo.getLastName());

        return userRepository.save(user);
    }

    public Map<Long, Boolean> deleteUser(Long id) {
        final User user = getUserById(id);

        userRepository.delete(user);

        Map<Long, Boolean> deleteResult = new HashMap<>();
        deleteResult.put(id, Boolean.TRUE);

        return deleteResult;
    }
}
