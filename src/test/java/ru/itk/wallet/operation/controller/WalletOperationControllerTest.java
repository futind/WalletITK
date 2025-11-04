package ru.itk.wallet.operation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.itk.wallet.operation.dto.WalletOperationInformation;
import ru.itk.wallet.operation.dto.WalletOperationResponse;
import ru.itk.wallet.operation.dto.enumerations.OperationType;
import ru.itk.wallet.operation.exception.OperationForbiddenException;
import ru.itk.wallet.operation.service.WalletOperationService;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = WalletOperationController.class)
class WalletOperationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WalletOperationService operationService;

    private static final String BASE_URI = "/api/v1/wallet";

    @Test
    void postWithValidIdDepositReturnsValidResponse() throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal walletBefore = BigDecimal.ONE;
        BigDecimal amount = new BigDecimal("123321.55");
        WalletOperationInformation info = new WalletOperationInformation(walletId, OperationType.DEPOSIT, amount);

        BigDecimal resultingAmount = walletBefore.add(amount);

        when(operationService.operate(any(WalletOperationInformation.class))).thenReturn(new WalletOperationResponse(walletId, resultingAmount));

        MvcResult mvcResult = mockMvc.perform(
                        post(new URI(BASE_URI))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(info))
                )
                .andExpect(status().isOk())
                .andReturn();

        WalletOperationResponse result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WalletOperationResponse.class);

        assertNotNull(result);
        assertEquals(walletId, result.getWalletId());
        assertEquals(resultingAmount, result.getBalance());
    }

    @Test
    void postWithValidIdWithdrawReturnsValidResponseWhenSufficientFundsPresent() throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal walletBefore = new BigDecimal("123456.78");
        BigDecimal amount = new BigDecimal("123321.55");
        WalletOperationInformation info = new WalletOperationInformation(walletId, OperationType.WITHDRAW, amount);

        BigDecimal resultingAmount = walletBefore.subtract(amount);

        when(operationService.operate(any(WalletOperationInformation.class))).thenReturn(new WalletOperationResponse(walletId, resultingAmount));

        MvcResult mvcResult = mockMvc.perform(
                        post(new URI(BASE_URI))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(info))
                )
                .andExpect(status().isOk())
                .andReturn();

        WalletOperationResponse result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WalletOperationResponse.class);

        assertNotNull(result);
        assertEquals(walletId, result.getWalletId());
        assertEquals(resultingAmount, result.getBalance());
    }

    @ParameterizedTest
    @EnumSource(value = OperationType.class, names = {"DEPOSIT", "WITHDRAW"})
    void postWithRandomIdWithdrawThrowsNotFound(OperationType operationType) throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("123321.55");
        WalletOperationInformation info = new WalletOperationInformation(walletId, operationType, amount);

        when(operationService.operate(any(WalletOperationInformation.class))).thenThrow(new WalletNotFoundException());

        mockMvc.perform(post(new URI(BASE_URI))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(info))
                ).andExpect(status().isNotFound());
    }

    @Test
    void postWithValidIdWithdrawThrowsWhenInsufficientFunds() throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("123321.55");
        WalletOperationInformation info = new WalletOperationInformation(walletId, OperationType.WITHDRAW, amount);

        when(operationService.operate(any(WalletOperationInformation.class))).thenThrow(new OperationForbiddenException(OperationType.WITHDRAW));

        mockMvc.perform(post(new URI(BASE_URI))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info))
        ).andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @EnumSource(value = OperationType.class, names = {"DEPOSIT", "WITHDRAW"})
    void postWithValidIdButWithNonPositiveAmountFailsValidation(OperationType operationType) throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("-123321.55");
        WalletOperationInformation info = new WalletOperationInformation(walletId, operationType, amount);

        when(operationService.operate(info)).thenThrow(new WalletNotFoundException());

        mockMvc.perform(post(new URI(BASE_URI))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info))
        ).andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @EnumSource(value = OperationType.class, names = {"DEPOSIT", "WITHDRAW"})
    void postWithInvalidIdFailsValidation(OperationType operationType) throws Exception {
        BigDecimal amount = new BigDecimal("123321.55");
        WalletOperationInformation info = new WalletOperationInformation(null, operationType, amount);

        when(operationService.operate(info)).thenThrow(new WalletNotFoundException());

        mockMvc.perform(post(new URI(BASE_URI))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info))
        ).andExpect(status().isBadRequest());
    }
}