package com.viventor.repository;

import com.viventor.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByAccountId(Long accountId);
    Account findAccountByAccountNum(String accountNum);
}