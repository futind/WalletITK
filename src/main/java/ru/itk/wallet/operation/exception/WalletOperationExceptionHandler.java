package ru.itk.wallet.operation.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.itk.wallet.operation.controller.WalletOperationController;

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
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Throwable ex, WebRequest request) {
        // todo: implement handler
        return null;
    }

    /**
     * Handler for exceptions which are thrown when trying to perform an operation which can not be allowed at that moment
     * @param ex - exception (thrown when trying to perform a forbidden operation) // sound funny bruh
     * @param request - request in which exception was thrown
     * @return RFC 9457 compliant {@link ErrorResponse} wrapped in {@link ResponseEntity} for convenience
     */
    @ExceptionHandler(value = {OperationForbiddenException.class})
    public ResponseEntity<ErrorResponse> handleOperationForbiddenException(Throwable ex, WebRequest request) {
        // todo: implement handler
        return null;
    }

    // todo: snatch WalletNotFoundException from feature/balance branch and implement a handler for it here to
    // OR! Make a util Controller advice for exceptions common for both controllers (is it worth it in a long run tho?)
    // e.g. i make this now, then add N controllers, some of their exception could be handled by some, some by others.
    // maybe i should separate handlers by domain, e.g. not a controlleradvice for certain controllers, but for certain types of exceptions
    // idk who cares even if there's N controllers with overlapping exceptions, it is just linear shit, so i have a controller and an advice for it
    // is it that bad? it might be though i guess at some point
    // on the other hand - it is wrong in context of vertical slice architecture. If i bind my exception handling to some util tool
    // it means that i can't just pick this feature off this project and insert into another one which is kinda shit
    // yeah, i copy the code i guess, but that way it is much more modular


}
