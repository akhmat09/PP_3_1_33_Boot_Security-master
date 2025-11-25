package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== STARTING DATA INITIALIZATION ===");
        initializeRoles();
        initializeUsers();
        System.out.println("=== DATA INITIALIZATION COMPLETED ===");
    }

    private void initializeRoles() {
        if (roleService.findByName("ROLE_ADMIN") == null) {
            roleService.saveRole(new Role("ROLE_ADMIN"));
            System.out.println("Created role: ROLE_ADMIN");
        } else {
            System.out.println("Role ROLE_ADMIN already exists");
        }

        if (roleService.findByName("ROLE_USER") == null) {
            roleService.saveRole(new Role("ROLE_USER"));
            System.out.println("Created role: ROLE_USER");
        } else {
            System.out.println("Role ROLE_USER already exists");
        }
    }

    private void initializeUsers() {
        // Create admin user
        if (userService.findByUsername("admin@mail.ru") == null) {
            User admin = new User();
            admin.setEmail("admin@mail.ru");
            admin.setPassword("admin");
            admin.setFirstName("Admin");
            admin.setLastName("Adminov");
            admin.setAge(35);


            userService.saveUserWithRoles(admin, List.of(1L, 2L)); // ADMIN и USER
            System.out.println("Created admin user: admin@mail.ru / admin");
        } else {
            System.out.println("Admin user already exists");
        }


        if (userService.findByUsername("user@mail.ru") == null) {
            User user = new User();
            user.setEmail("user@mail.ru");
            user.setPassword("user");
            user.setFirstName("User");
            user.setLastName("Userov");
            user.setAge(30);


            userService.saveUserWithRoles(user, List.of(2L)); // USER
            System.out.println("Created user: user@mail.ru / user");
        } else {
            System.out.println("User user already exists");
        }
    }
}