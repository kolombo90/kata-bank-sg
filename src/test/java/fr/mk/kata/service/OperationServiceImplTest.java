package fr.mk.kata.service;

import fr.mk.kata.exception.AccountNotFoundException;
import fr.mk.kata.exception.BalanceException;
import fr.mk.kata.model.Account;
import fr.mk.kata.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private OperationServiceImpl operationServiceImpl;

    @Test(expected = AccountNotFoundException.class)
    public void should_throw_exception_when_account_is_not_found() throws BalanceException, AccountNotFoundException {
        Optional<Account> notFoundAccount = Optional.empty();
        doReturn(notFoundAccount).when(accountRepository).findById(1L);
        operationServiceImpl.fetchBy(1L);
    }

}
