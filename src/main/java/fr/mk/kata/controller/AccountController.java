package fr.mk.kata.controller;

import fr.mk.kata.dto.AccountResponsePayload;
import fr.mk.kata.dto.OperationDto;
import fr.mk.kata.exception.AccountNotFoundException;
import fr.mk.kata.exception.BalanceException;
import fr.mk.kata.model.Operation;
import fr.mk.kata.service.AccountService;
import fr.mk.kata.service.AccountServiceImpl;
import fr.mk.kata.service.OperationService;
import fr.mk.kata.service.OperationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController {

    private AccountService accountService;

    private OperationService operationService;

    @Autowired
    public AccountController(AccountService accountService, OperationService operationService){
        this.accountService = accountService;
        this.operationService = operationService;
    }

    public AccountController(AccountServiceImpl accountServiceImpl) {
        this.accountService = accountServiceImpl;
    }

    @PostMapping("/accounts/{accountId}/balance/update")
    public AccountResponsePayload updateBalance(@Valid @RequestBody OperationDto operationDto, @PathVariable Long accountId) throws BalanceException, AccountNotFoundException {
        return accountService.updateBalance(operationDto , accountId);
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<Operation> getAccountHistory(@PathVariable Long accountId) throws AccountNotFoundException {
        return operationService.fetchBy(accountId);
    }
}
