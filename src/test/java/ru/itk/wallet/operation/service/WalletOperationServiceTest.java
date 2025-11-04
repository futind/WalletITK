package ru.itk.wallet.operation.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.wallet.operation.dto.WalletOperationInformation;
import ru.itk.wallet.operation.dto.WalletOperationResponse;
import ru.itk.wallet.operation.dto.enumerations.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WalletOperationServiceTest {

    @Mock
    private DepositService depositService;

    @Mock
    private WithdrawService withdrawService;

    @InjectMocks
    private WalletOperationService operationService;

    @Test
    void depositCallsDepositService() {
        when(depositService.deposit(any(UUID.class), any(BigDecimal.class))).thenReturn(null);

        operationService.operate(new WalletOperationInformation(UUID.randomUUID(), OperationType.DEPOSIT, BigDecimal.ZERO));
        verify(depositService, times(1)).deposit(any(UUID.class), any(BigDecimal.class));
        verifyNoInteractions(withdrawService);
    }

    @Test
    void withdrawCallsWithdrawService() {
        when(withdrawService.withdraw(any(UUID.class), any(BigDecimal.class))).thenReturn(null);

        operationService.operate(new WalletOperationInformation(UUID.randomUUID(), OperationType.WITHDRAW, BigDecimal.ZERO));
        verify(withdrawService, times(1)).withdraw(any(UUID.class), any(BigDecimal.class));
        verifyNoInteractions(depositService);
    }

}