package fr.mk.kata.service;

import fr.mk.kata.exception.AccountNotFoundException;
import fr.mk.kata.model.Operation;

import java.util.List;

public interface OperationService {
    public List<Operation> fetchBy(Long accountId) throws AccountNotFoundException;
}
