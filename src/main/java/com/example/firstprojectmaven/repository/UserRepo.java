package com.example.firstprojectmaven.repository;

import com.example.firstprojectmaven.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);
}