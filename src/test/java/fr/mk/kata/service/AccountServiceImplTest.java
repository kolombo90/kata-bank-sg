package fr.mk.kata.service;

import fr.mk.kata.dto.AccountResponsePayload;
import fr.mk.kata.dto.OperationDto;
import fr.mk.kata.exception.AccountNotFoundException;
import fr.mk.kata.exception.BalanceException;
import fr.mk.kata.model.Account;
import fr.mk.kata.repository.AccountRepository;
import fr.mk.kata.repository.OperationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    private Account account, updatedAccount;

    Validator validator;
    ValidatorFactory factory;
    OperationDto operationDto;


    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Before
    public void init() {

        account = Account.builder()
                .id(1L)
                .balance(0L).build();
        updatedAccount = Account.builder()
                .id(1L)
                .balance(100L).build();

        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        operationDto = operationDto
                .builder()
                .operationType("WITHDRAW")
                .amount(100L)
                .build();
    }

    @Test
    public void should_have_violation_when_amount_is_negatif() {
        operationDto.setAmount(-3L);
        Set<ConstraintViolation<OperationDto>> constraintViolations = validator.validate(operationDto);
        assertTrue(constraintViolations.size() > 0);
    }

    @Test
    public void should_have_violation_when_operation_is_not_valid() {
        operationDto.setAmount(100L);
        operationDto.setOperationType("invalidOperation");
        Set<ConstraintViolation<OperationDto>> constraintViolations = validator.validate(operationDto);
        assertTrue(constraintViolations.size() > 0);
    }

    @Test
    public void should_not_have_violation() {
        Set<ConstraintViolation<OperationDto>> constraintViolations = validator.validate(operationDto);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test(expected = BalanceException.class)
    public void should_throw_exception_when_withdraw_amount_is_more_than_balance() throws BalanceException, AccountNotFoundException {
        doReturn(Optional.of(account)).when(accountRepository).findById(1L);
        accountServiceImpl.updateBalance(operationDto, 1L);
    }

    @Test(expected = AccountNotFoundException.class)
    public void should_throw_exception_when_account_is_not_found() throws BalanceException, AccountNotFoundException {
        Optional<Account> notFoundAccount = Optional.empty();
        doReturn(notFoundAccount).when(accountRepository).findById(1L);
        accountServiceImpl.updateBalance(operationDto, 1L);
    }

    @Test
    public void should_update_balance() throws BalanceException, AccountNotFoundException {
        operationDto.setOperationType("DEPOSIT");
        doReturn(Optional.of(account)).when(accountRepository).findById(1L);
        doReturn(updatedAccount).when(accountRepository).save(updatedAccount);

        AccountResponsePayload result = accountServiceImpl.updateBalance(operationDto, 1L);

        assertEquals(result.getActualBalance(), updatedAccount.getBalance());
    }

}
