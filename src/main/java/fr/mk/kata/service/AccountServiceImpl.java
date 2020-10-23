package fr.mk.kata.service;

import fr.mk.kata.dto.AccountResponsePayload;
import fr.mk.kata.dto.OperationDto;
import fr.mk.kata.exception.AccountNotFoundException;
import fr.mk.kata.exception.BalanceException;
import fr.mk.kata.model.Account;
import fr.mk.kata.model.Operation;
import fr.mk.kata.model.OperationType;
import fr.mk.kata.repository.AccountRepository;
import fr.mk.kata.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static fr.mk.kata.model.OperationType.DEPOSIT;
import static fr.mk.kata.model.OperationType.WITHDRAW;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Transactional
    public AccountResponsePayload updateBalance(OperationDto operationDto, Long accountId) throws BalanceException, AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Please provide a valid accountId"));

        if (!checkOperation(operationDto, account)) {
            throw new BalanceException("Insufficient balance for this operation");
        }

        account.changeBalance(operationDto.getAmount(),OperationType.valueOf(operationDto.getOperationType()));
        accountRepository.save(account);
        operationRepository.save(Operation
                .builder()
                .date(new Date())
                .amount(operationDto.getAmount())
                .operationType(operationDto.getOperationType())
                .balance(account.getBalance())
                .account(account)
                .build());

        return AccountResponsePayload
                .builder()
                .result(operationDto.getOperationType() + " done with success")
                .actualBalance(account.getBalance())
                .build();
    }

    private boolean checkOperation(OperationDto operationDto, Account acount) {
        if (operationDto.getAmount() > acount.getBalance() && OperationType.valueOf(operationDto.getOperationType()) == WITHDRAW) {
            return false;
        }
        return true;
    }
}
