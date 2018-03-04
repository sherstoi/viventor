package com.viventor.service;

import com.viventor.dto.*;
import com.viventor.entity.Account;
import com.viventor.entity.Client;
import com.viventor.repository.AccountRepository;
import com.viventor.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Service
public class BankService {
    @Resource
    private UserService userService;

    @Resource
    private ClientRepository clientRepository;

    @Resource
    private AccountRepository accountRepository;

    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO, String userLogin) {
        ApplicationUserDTO applicationUserDTO = userService.findAppUserByLogin(userLogin);
        if (applicationUserDTO == null) {
            throw new RuntimeException("Information about logged user doesn't exists in DB!");
        }
        clientDTO.setApplicationUserDTO(applicationUserDTO);
        Client client = ClientMapper.INSTANCE.toClient(clientDTO);
        client = clientRepository.save(client);
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO, String userLogin) {
        Account account = null;
        ApplicationUserDTO applicationUserDTO = userService.findAppUserByLogin(userLogin);
        if (applicationUserDTO != null) {
            ClientDTO clientDTO = findClientByAppUserId(applicationUserDTO.getUserId());
            if (clientDTO != null) {
                accountDTO.setClientId(clientDTO.getClientId());
                if (StringUtils.isEmpty(accountDTO.getAccountNum())) {
                    accountDTO.setAccountNum(UUID.randomUUID().toString());
                }
                account = AccountMapper.INSTANCE.toAccount(accountDTO);
                account = accountRepository.save(account);
            }
        }

        return AccountMapper.INSTANCE.toAccountResponse(account);
    }

    @Transactional
    public AccountDTO deposit(AccountDTO accountReq) {
        Account account = accountRepository.findAccountByAccountNum(accountReq.getAccountNum());
        AccountDTO accountDTO = AccountMapper.INSTANCE.toAccountResponse(account);
        if (account != null) {
            BigDecimal sum = accountDTO.getBalance().add(accountReq.getBalance());
            accountDTO.setBalance(sum);
            account = AccountMapper.INSTANCE.toAccount(accountDTO);
            account = accountRepository.save(account);
        }
        return AccountMapper.INSTANCE.toAccountResponse(account);
    }

    @Transactional
    public AccountDTO withdrawn(AccountDTO accountReq) {
        Account account = accountRepository.findAccountByAccountNum(accountReq.getAccountNum());
        AccountDTO accountDTO = AccountMapper.INSTANCE.toAccountResponse(account);
        if (accountDTO != null) {
            BigDecimal sum = accountDTO.getBalance().subtract(accountReq.getBalance());
            accountDTO.setBalance(sum);
            account = AccountMapper.INSTANCE.toAccount(accountDTO);
            account = accountRepository.save(account);
        }
        return AccountMapper.INSTANCE.toAccountResponse(account);
    }

    public Set<AccountDTO> reveceiveClientAccountStatements(String login) {
        ApplicationUserDTO applicationUserDTO = userService.findAppUserByLogin(login);
        ClientDTO clientDTO = findClientByAppUserId(applicationUserDTO.getUserId());
        return clientDTO.getAccountDTOs();
    }

    private ClientDTO findClientByAppUserId(Long appUsrId) {
        Client client = clientRepository.findClientByApplicationUserName(appUsrId);
        return ClientMapper.INSTANCE.toClientResponse(client);
    }
}
