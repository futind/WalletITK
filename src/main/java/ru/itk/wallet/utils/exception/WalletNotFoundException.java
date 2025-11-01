package ru.itk.wallet.utils.exception;

import java.util.UUID;

/**
 * An exception indicating that a wallet with a particular id was not found in the database.
 */
public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException() {
        super("Wallet not found!");
    }
}
