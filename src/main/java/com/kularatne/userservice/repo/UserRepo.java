package com.kularatne.userservice.repo;

import com.kularatne.userservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByNameame(String username);
}
