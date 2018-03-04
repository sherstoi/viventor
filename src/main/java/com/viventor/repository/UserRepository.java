package com.viventor.repository;

import com.viventor.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findUserByUserName(String userName);
    ApplicationUser findUserByUserId(Long userId);
}