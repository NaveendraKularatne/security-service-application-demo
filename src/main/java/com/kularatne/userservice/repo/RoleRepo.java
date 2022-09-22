package com.kularatne.userservice.repo;

import com.kularatne.userservice.domain.Role;
import com.kularatne.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String username);
}
