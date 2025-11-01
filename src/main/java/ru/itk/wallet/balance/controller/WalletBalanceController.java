package ru.itk.wallet.balance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.itk.wallet.balance.api.WalletBalanceAPI;
import ru.itk.wallet.balance.dto.WalletBalanceResponse;

import java.util.UUID;

@RestController
public class WalletBalanceController implements WalletBalanceAPI {

    /**
     * GET(/api/v1/wallets/{wallet_uuid}
     * A method used in order to check the balance of a particular wallet
     *
     * @param walletId - unique identifier ({@link UUID}) of wallet
     * @return {@link WalletBalanceResponse} - information about wallet's balance
     */
    @Override
    public ResponseEntity<WalletBalanceResponse> getBalance(UUID walletId) {
        return null;
    }
}
