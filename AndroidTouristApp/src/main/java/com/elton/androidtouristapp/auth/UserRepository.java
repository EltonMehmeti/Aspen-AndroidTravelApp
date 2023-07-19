package com.elton.androidtouristapp.auth;

import com.elton.androidtouristapp.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}

