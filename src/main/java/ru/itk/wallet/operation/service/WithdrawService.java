package ru.itk.wallet.operation.service;

import org.springframework.stereotype.Service;
import ru.itk.wallet.operation.dto.WalletOperationResponse;
import ru.itk.wallet.operation.exception.NonPositiveAmountException;
import ru.itk.wallet.operation.repository.WithdrawDataAccess;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service implementing withdrawing functionality
 */
@Service
public class WithdrawService {

    private final WithdrawDataAccess withdrawDAO;

    public WithdrawService(WithdrawDataAccess withdrawDAO) {
        this.withdrawDAO = withdrawDAO;
    }

    /**
     * A method which allows to withdraw a certain amount from a wallet
     * @param walletId - unique identifier of a wallet
     * @param amountToWithdraw - an amount of money to withdraw from the wallet
     * @return {@link WalletOperationResponse} - information about the wallet and updated amount of money on the account
     * @throws NonPositiveAmountException - if amountToWithdraw is negative or zero
     * @throws ru.itk.wallet.utils.exception.WalletNotFoundException - if the wallet with walletId does not exist
     * @throws ru.itk.wallet.operation.exception.OperationForbiddenException - if the wallet does not have a sufficient amount of money
     */
    public WalletOperationResponse withdraw(UUID walletId, BigDecimal amountToWithdraw) {
        return new WalletOperationResponse(walletId, withdrawDAO.withdraw(walletId, amountToWithdraw));
    }
}
