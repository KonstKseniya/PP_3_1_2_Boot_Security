package ru.kata.spring.boot_security.demo.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;


@Component
@Transactional
public class ApplicationStart implements ApplicationRunner {
    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationStart(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        entityManager.persist(new Role("USER"));
        entityManager.persist(new Role("ADMIN"));
        Role userRole = entityManager.find(Role.class, 1);
        Role adminRole = entityManager.find(Role.class, 2);

        User user = new User();
        user.setUsername("user");
        user.setEmail("user@user.com");
        user.setBio("i am user");
        user.setPassword(passwordEncoder.encode("user"));
        user.addRole(userRole);
        entityManager.persist(user);

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@admin.com");
        admin.setBio("i am admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.addRole(adminRole);
        entityManager.persist(admin);
    }
}
