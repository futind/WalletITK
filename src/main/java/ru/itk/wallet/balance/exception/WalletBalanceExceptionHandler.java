package ru.itk.wallet.balance.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.itk.wallet.balance.controller.WalletBalanceController;
import ru.itk.wallet.utils.exception.ErrorResponseFactory;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

/**
 * Exception handler class for WalletBalanceController
 */
@ControllerAdvice(assignableTypes = {WalletBalanceController.class})
public class WalletBalanceExceptionHandler {

    /**
     * A method which handles NotFound exceptions
     * @param ex - the exception raised
     * @param request - request in which the exception had been raised
     * @return RFC 9457 {@link ErrorResponse} wrapped into {@link ResponseEntity}
     */
    @ExceptionHandler(value = {WalletNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleWalletNotFoundException(WalletNotFoundException ex, WebRequest request) {
        return ErrorResponseFactory.create(HttpStatus.NOT_FOUND, ex, request);
    }

    /**
     * A method which handles validation exception (type mismatch)
     * @param ex - the exception raised
     * @param request - request in which the exception had been raised
     * @return RFC 9457 {@link ErrorResponse} wrapped into {@link ResponseEntity}
     */
    @ExceptionHandler(value = {
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class, 
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return ErrorResponseFactory.create(HttpStatus.BAD_REQUEST, ex, request);
    }
}
