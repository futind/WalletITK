package ru.itk.wallet.operation.repository;

import java.math.BigDecimal;
import java.util.UUID;

public interface WithdrawDataAccess {

    /**
     * A method which allows user to withdraw funds from their wallet (if such a wallet exists).
     * Operation can be disallowed if funds are insufficient.
     * @param walletId - unique identifier of wallet (UUID).
     * @param amountToWithdraw - an amount of money user wants to withdraw
     * @return updated balance of a wallet
     * @throws ru.itk.wallet.operation.exception.OperationForbiddenException - if there is not enough funds on the user's account
     * @throws ru.itk.wallet.operation.exception.NonPositiveAmountException - if amountToWithdraw is negative or zero
     * todo: throws WalletNotFoundException
     */
    BigDecimal withdraw(UUID walletId, BigDecimal amountToWithdraw);
}
