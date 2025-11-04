package ru.itk.wallet.balance.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.itk.wallet.balance.dto.WalletBalanceResponse;
import ru.itk.wallet.balance.repository.BalanceDataAccess;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletBalanceServiceTest {

    @Mock
    private BalanceDataAccess balanceDAO;

    @InjectMocks
    private WalletBalanceService balanceService;

    @Test
    void whenGivenCorrectIdReturnsCorrectDTO() {
        BigDecimal expectedAmount = new BigDecimal("12345.67");
        UUID expectedId = UUID.randomUUID();

        when(balanceDAO.getBalance(expectedId)).thenReturn(expectedAmount);
        WalletBalanceResponse response = balanceService.getBalance(expectedId);
        assertEquals(expectedId, response.getWalletId());
        assertEquals(expectedAmount, response.getBalance());
    }

    @Test
    void whenDAOThrowsThenDoesNotCatch() {
        when(balanceDAO.getBalance(any(UUID.class))).thenThrow(new WalletNotFoundException());

        assertThrowsExactly(
                WalletNotFoundException.class,
                () -> balanceDAO.getBalance(UUID.randomUUID())
        );
    }




}