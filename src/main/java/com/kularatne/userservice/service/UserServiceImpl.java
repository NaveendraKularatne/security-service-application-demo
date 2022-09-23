package com.kularatne.userservice.service;

import com.kularatne.userservice.domain.Role;
import com.kularatne.userservice.domain.User;
import com.kularatne.userservice.repo.RoleRepo;
import com.kularatne.userservice.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public User saveUser(User user) {
        if (user.getName() == null) {
            throw new RuntimeException("User name cannot be empty");
        } else if (user.getName().length() > 50) {
            throw new RuntimeException("Name length cannot be more than 50");
        }
        log.info("Saving new user {} to the database", user.getName());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User foundUser = userRepo.findByUsername(username);
        Role foundRoles = roleRepo.findByName(roleName);

        foundUser.getRoles().add(foundRoles); /* After finding the user and role/s from the database get whatever
        the roles that user has on db, and add this role/s to those previous role/s
        If this is an industrially used app, we may have to do validations on userName and role name
        */
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {} ", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }
}
