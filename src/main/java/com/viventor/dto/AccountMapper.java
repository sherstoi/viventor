package com.viventor.dto;

import com.viventor.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDTO toAccountResponse(Account account);
    Set<AccountDTO> toAccountResponses(Set<Account> accounts);

    Account toAccount(AccountDTO accountDTO);
    Set<Account> toAccounts(Set<AccountDTO> accountDTOs);
}