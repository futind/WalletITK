package ru.itk.wallet.balance.repository;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Interface which sets up the contract of a data access object needed in order to get a balance of an account (wallet)
 */
public interface BalanceDataAccess {

    /**
     * Gets the balance of a wallet from a database
     * @param walletId - id of a particular wallet
     * @return balance of a wallet {@link BigDecimal}
     */
    BigDecimal getBalance(UUID walletId);
}
