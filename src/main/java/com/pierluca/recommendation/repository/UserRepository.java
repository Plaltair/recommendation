package com.pierluca.recommendation.repository;

import com.pierluca.recommendation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
