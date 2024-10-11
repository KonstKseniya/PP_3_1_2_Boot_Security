package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);

    User getUserByUsername(String username);

    List<User> getAllUsersWithRole();

    void saveUser(User user);

    void deleteUser(Long id);

    User getUserById(long id);
}