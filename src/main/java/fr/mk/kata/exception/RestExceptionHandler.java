package fr.mk.kata.exception;

import fr.mk.kata.dto.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo balanceExceptionHandler (BalanceException ex) {
        ErrorInfo errorInfo = ErrorInfo.builder().errorMessage(ex.getMessage()).build();
        return errorInfo;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo accountNotFoundExceptionHandler (AccountNotFoundException ex) {
        ErrorInfo errorInfo = ErrorInfo.builder().errorMessage(ex.getMessage()).build();
        return errorInfo;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorInfo> bindExceptionHandler(MethodArgumentNotValidException ex) {
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        List<ErrorInfo> errorInfos = new ArrayList<>();
        errorList.stream().map(ObjectError::getDefaultMessage).forEach(msg -> errorInfos.add(ErrorInfo.builder().errorMessage(msg).build()));
        return errorInfos;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo bindExceptionHandler(HttpMessageNotReadableException ex) {
        return ErrorInfo.builder().errorMessage("Could not parse the input object please check").build();
    }

}