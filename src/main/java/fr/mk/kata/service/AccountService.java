package fr.mk.kata.service;

import fr.mk.kata.dto.AccountResponsePayload;
import fr.mk.kata.dto.OperationDto;
import fr.mk.kata.exception.AccountNotFoundException;
import fr.mk.kata.exception.BalanceException;

public interface AccountService {
    public AccountResponsePayload updateBalance(OperationDto operationDto, Long accountId) throws BalanceException, AccountNotFoundException;
}
