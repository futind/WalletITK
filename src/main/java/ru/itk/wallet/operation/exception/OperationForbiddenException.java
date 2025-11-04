package ru.itk.wallet.operation.exception;

import ru.itk.wallet.operation.dto.enumerations.OperationType;

/**
 * Exception which is thrown if operation a user wants to perform can't be allowed (for one reason or other)
 * E.g. User tries to withdraw 100 money units from a wallet with 99 money units, etc.
 */
public class OperationForbiddenException extends RuntimeException {
    public OperationForbiddenException(OperationType operationType) {
        super("This operation can't be performed! Type:" + operationType.toString());
    }
}
