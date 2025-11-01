package ru.itk.wallet.operation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itk.wallet.operation.api.WalletOperationAPI;
import ru.itk.wallet.operation.dto.WalletOperationInformation;

@RestController("/api/v1")
public class WalletOperationController implements WalletOperationAPI {

    /**
     * POST(/api/v1/wallet)
     * A method which allows to use the wallet (to make a deposit/a withdrawal)
     * @param walletInfo - information about a wallet, an operation and about the desired amount to deposit/withdraw {@link WalletOperationInformation}
     */
    @Override
    @PostMapping("/wallet")
    public ResponseEntity<Void> operateOnAWallet(WalletOperationInformation walletInfo) {
        return null;
    }
}
