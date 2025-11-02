package ru.itk.wallet.operation.exception;

/**
 * Exception thrown when trying to pass non-positive amount into deposit/withdraw methods
 */
public class NonPositiveAmountException extends RuntimeException {
    public NonPositiveAmountException() {
        super("Impossible to perform operation with non-positive amount of money!");
    }
}
