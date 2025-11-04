package ru.itk.wallet.operation.service;

import org.springframework.stereotype.Service;
import ru.itk.wallet.operation.dto.WalletOperationInformation;
import ru.itk.wallet.operation.dto.WalletOperationResponse;
import ru.itk.wallet.operation.exception.NonPositiveAmountException;
import ru.itk.wallet.operation.repository.DepositDataAccess;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service implementing depositing functionality
 */
@Service
public class DepositService {

    private final DepositDataAccess depositDAO;

    public DepositService(DepositDataAccess depositDAO) {
        this.depositDAO = depositDAO;
    }

    /**
     * A method which allows to deposit a certain amount to a wallet
     * @param walletId - unique identifier of a wallet
     * @param amountToDeposit - an amount of money to deposit to the wallet
     * @return {@link WalletOperationResponse} - information about the wallet and updated amount of money on the account
     * @throws NonPositiveAmountException - if amountToDeposit is negative or zero
     * @throws ru.itk.wallet.utils.exception.WalletNotFoundException - if the wallet with walletId does not exist
     */
    public WalletOperationResponse deposit(UUID walletId, BigDecimal amountToDeposit) {
        return new WalletOperationResponse(walletId, depositDAO.deposit(walletId, amountToDeposit));
    }
}
