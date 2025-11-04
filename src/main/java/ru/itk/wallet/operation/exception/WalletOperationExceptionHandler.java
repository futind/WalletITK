package ru.itk.wallet.operation.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.itk.wallet.operation.controller.WalletOperationController;
import ru.itk.wallet.utils.exception.ErrorResponseFactory;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

/**
 * Controller advice with exception handlers for wallet operation feature
 */
@ControllerAdvice(assignableTypes = {WalletOperationController.class})
public class WalletOperationExceptionHandler {

    /**
     * Handler for exceptions which are concerned with validation failing
     * @param ex - exception (thrown when validation had been failed)
     * @param request - request in which exception was thrown
     * @return RFC 9457 compliant {@link ErrorResponse} wrapped in {@link ResponseEntity} for convenience
     */
    @ExceptionHandler(value = {ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Throwable ex, WebRequest request) {
        return ErrorResponseFactory.create(HttpStatus.BAD_REQUEST, ex, request);
    }

    /**
     * Handler for exceptions which are thrown when trying to perform an operation
     * which can not be allowed at that moment (or with wrong parameters)
     * @param ex - exception (thrown when trying to perform a forbidden operation) // sound funny bruh
     * @param request - request in which exception was thrown
     * @return RFC 9457 compliant {@link ErrorResponse} wrapped in {@link ResponseEntity} for convenience
     */
    @ExceptionHandler(value = {OperationForbiddenException.class, NonPositiveAmountException.class})
    public ResponseEntity<ErrorResponse> handleOperationForbiddenException(Throwable ex, WebRequest request) {
        return ErrorResponseFactory.create(HttpStatus.FORBIDDEN, ex, request);
    }

    /**
     * Handler for exception which are thrown when trying to perform any operation
     * with a wallet that does not exist in the database
     * @param ex - exception (thrown when wallet we try to operate with does not exist)
     * @param request - request in which exception was thrown
     * @return RFC 9457 compliant {@link ErrorResponse} wrapped in {@link ResponseEntity} for convenience
     */
    @ExceptionHandler(value = {WalletNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Throwable ex, WebRequest request) {
        return ErrorResponseFactory.create(HttpStatus.NOT_FOUND, ex, request);
    }

}
