package ru.itk.wallet.operation.service;

import org.springframework.stereotype.Service;
import ru.itk.wallet.operation.dto.WalletOperationInformation;
import ru.itk.wallet.operation.dto.WalletOperationResponse;
import ru.itk.wallet.operation.dto.enumerations.OperationType;
import ru.itk.wallet.operation.exception.NonPositiveAmountException;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Service which uses other services to implement requested functionality;
 * used in the controller {@link ru.itk.wallet.operation.controller.WalletOperationController}
 */
@Service
public class WalletOperationService {

    private final DepositService depositService;
    private final WithdrawService withdrawService;

    public WalletOperationService(DepositService depositService,
                                  WithdrawService withdrawService) {
        this.depositService = depositService;
        this.withdrawService = withdrawService;
    }

    /**
     * A method which operates with a wallet
     * @param walletOperationInformation - information about the wallet, operation with it and amount of that operation
     * @return {@link WalletOperationResponse} - information about the wallet and updated balance of that wallet
     */
    public WalletOperationResponse operate(WalletOperationInformation walletOperationInformation) {

        BigDecimal operationAmount = walletOperationInformation.getAmount();
        UUID walletId = walletOperationInformation.getWalletId();

        return switch (walletOperationInformation.getOperationType()) {
            case DEPOSIT -> depositService.deposit(walletId, operationAmount);
            case WITHDRAW -> withdrawService.withdraw(walletId, operationAmount);
        };
    }
}
