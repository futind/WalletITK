package ru.itk.wallet.operation.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itk.wallet.operation.api.WalletOperationAPI;
import ru.itk.wallet.operation.dto.WalletOperationInformation;
import ru.itk.wallet.operation.dto.WalletOperationResponse;
import ru.itk.wallet.operation.service.WalletOperationService;

@RestController
@RequestMapping("/api/v1")
public class WalletOperationController implements WalletOperationAPI {

    private final WalletOperationService walletOperationService;

    public WalletOperationController(WalletOperationService walletOperationService) {
        this.walletOperationService = walletOperationService;
    }

    /**
     * POST(/api/v1/wallet)
     * A method which allows to use the wallet (to make a deposit/a withdrawal)
     * @param walletOperationInformation - information about a wallet,
     *                                   an operation and about the desired amount to deposit/withdraw {@link WalletOperationInformation}
     */
    @Override
    @PostMapping("/wallet")
    public ResponseEntity<WalletOperationResponse> operateOnAWallet(@RequestBody @Valid WalletOperationInformation walletOperationInformation) {
        return ResponseEntity
                .ok()
                .body(walletOperationService.operate(walletOperationInformation));
    }
}
