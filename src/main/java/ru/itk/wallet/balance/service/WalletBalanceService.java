package ru.itk.wallet.balance.service;

import org.springframework.stereotype.Service;
import ru.itk.wallet.balance.repository.BalanceDataAccess;
import ru.itk.wallet.balance.dto.WalletBalanceResponse;

import java.util.UUID;

/**
 * Service for viewing the balance of a wallet
 */
@Service
public class WalletBalanceService {

    private final BalanceDataAccess balanceDAO;

    public WalletBalanceService(BalanceDataAccess balanceDAO) {
        this.balanceDAO = balanceDAO;
    }

    /**
     * Method which allows to see the balance of the wallet (at that particular moment).
     * It is guaranteed to return an accurate amount of money (at some point)/
     * But when user sees the data it may not be accurate, they might need to refresh, e.g., if there were pending transaction.
     * @param walletId - unique identifier of a wallet
     * @return {@link WalletBalanceResponse}
     * @throws ru.itk.wallet.utils.exception.WalletNotFoundException - if the wallet with given walletId does not exist
     */
    public WalletBalanceResponse getBalance(UUID walletId) {
        return new WalletBalanceResponse(walletId, balanceDAO.getBalance(walletId));
    }
}
