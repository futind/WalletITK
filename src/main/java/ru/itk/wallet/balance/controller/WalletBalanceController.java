package ru.itk.wallet.balance.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itk.wallet.balance.api.WalletBalanceAPI;
import ru.itk.wallet.balance.dto.WalletBalanceResponse;
import ru.itk.wallet.balance.service.WalletBalanceService;

import java.util.UUID;

/**
 * REST controller for "view balance" feature
 */
@RestController
@RequestMapping("/api/v1")
public class WalletBalanceController implements WalletBalanceAPI {

    private final WalletBalanceService balanceService;

    public WalletBalanceController(WalletBalanceService balanceService) {
        this.balanceService = balanceService;
    }

    /**
     * GET(/api/v1/wallets/{wallet_uuid}
     * A method used in order to check the balance of a particular wallet
     * @param walletId - unique identifier ({@link UUID}) of wallet
     * @return {@link WalletBalanceResponse} - information about wallet's balance
     */
    @Override
    @GetMapping("/wallets/{wallet_uuid}")
    public ResponseEntity<WalletBalanceResponse> getBalance(@PathVariable(name = "wallet_uuid") UUID walletId) {
        return ResponseEntity.ok().body(balanceService.getBalance(walletId));
    }
}
