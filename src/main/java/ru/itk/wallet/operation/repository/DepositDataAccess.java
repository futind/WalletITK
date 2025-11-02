package ru.itk.wallet.operation.repository;

import java.math.BigDecimal;
import java.util.UUID;

public interface DepositDataAccess {

    /**
     * A method which allows user to deposit some amount of money to their wallet.
     * @param walletId - unique identifier of a wallet (UUID).
     * @param amountToDeposit - amount of money user wants to deposit
     * @return updated amount of money on the account (in the wallet)
     * @throws ru.itk.wallet.operation.exception.NonPositiveAmountException - if amountToDeposit if negative or zero
     * todo: throws WalletNotFoundException
     */
    BigDecimal deposit(UUID walletId, BigDecimal amountToDeposit);
}
