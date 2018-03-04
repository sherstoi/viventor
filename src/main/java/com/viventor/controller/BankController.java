package com.viventor.controller;

import com.viventor.dto.AccountDTO;
import com.viventor.dto.ClientDTO;
import com.viventor.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@RequestMapping("/bank")
public class BankController {
    @Resource
    private BankService bankService;

    @RequestMapping(method = RequestMethod.POST, value = "/createClient")
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid @NotNull ClientDTO clientDTO, HttpServletRequest request) {
        return new ResponseEntity<>(bankService.createClient(clientDTO, request.getUserPrincipal().getName()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createAccount")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid @NotNull AccountDTO accountDTO, HttpServletRequest request) {
        return new ResponseEntity<>(bankService.createAccount(accountDTO, request.getUserPrincipal().getName()), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deposit")
    public ResponseEntity<AccountDTO> depositMoney(@RequestBody @Valid @NotNull AccountDTO accountDTO) {
        return new ResponseEntity<>(bankService.deposit(accountDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/withdrawn")
    public ResponseEntity<AccountDTO> withdrawnMoney(@RequestBody @Valid @NotNull AccountDTO accountDTO) {
        return new ResponseEntity<>(bankService.withdrawn(accountDTO), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/statements")
    public ResponseEntity<Set<AccountDTO>> readAccountStatements(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(bankService.reveceiveClientAccountStatements(
                httpServletRequest.getUserPrincipal().getName()), HttpStatus.OK);
    }
}