package com.viventor.repository;

import com.viventor.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findClientByClientName(String clientName);

    @Query(value = "SELECT * FROM CLIENT WHERE USER_ID = ?1", nativeQuery = true)
    Client findClientByApplicationUserName(Long userId);
}