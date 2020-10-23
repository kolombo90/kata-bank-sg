package fr.mk.kata.service;

import fr.mk.kata.exception.AccountNotFoundException;
import fr.mk.kata.model.Account;
import fr.mk.kata.model.Operation;
import fr.mk.kata.repository.AccountRepository;
import fr.mk.kata.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Operation> fetchBy(Long accountId) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Please provide a valid accountId"));
        return operationRepository.findByAccount(account);
    }
}
