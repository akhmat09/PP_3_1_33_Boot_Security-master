package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveUserWithRoles(User user, List<Long> roleIds) {

        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : roleIds) {
                Role role = roleService.getAllRoles().stream()
                        .filter(r -> r.getId().equals(roleId))
                        .findFirst()
                        .orElse(null);
                if (role != null) {
                    roles.add(role);
                }
            }
            user.setRoles(roles);
        } else {

            Role defaultRole = roleService.findByName("ROLE_USER");
            if (defaultRole != null) {
                user.setRoles(Set.of(defaultRole));
            }
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, User user) {
        User existingUser = getUserById(id);


        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());


        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser);
    }

    @Override
    public void updateUserWithRoles(Long id, User user, List<Long> roleIds) {
        User existingUser = getUserById(id);


        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());


        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }


        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : roleIds) {
                Role role = roleService.getAllRoles().stream()
                        .filter(r -> r.getId().equals(roleId))
                        .findFirst()
                        .orElse(null);
                if (role != null) {
                    roles.add(role);
                }
            }
            existingUser.setRoles(roles);
        } else {

            Role defaultRole = roleService.findByName("ROLE_USER");
            if (defaultRole != null) {
                existingUser.setRoles(Set.of(defaultRole));
            }
        }

        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}