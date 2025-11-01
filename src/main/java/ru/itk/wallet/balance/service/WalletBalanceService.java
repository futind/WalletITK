package ru.itk.wallet.balance.service;

import org.springframework.stereotype.Service;
import ru.itk.wallet.balance.repository.BalanceDataAccess;
import ru.itk.wallet.balance.dto.WalletBalanceResponse;

import java.util.UUID;

@Service
public class WalletBalanceService {

    private final BalanceDataAccess balanceDAO;

    public WalletBalanceService(BalanceDataAccess balanceDAO) {
        this.balanceDAO = balanceDAO;
    }

    public WalletBalanceResponse getBalance(UUID walletId) {
        return new WalletBalanceResponse(balanceDAO.getBalance(walletId));
    }
}
