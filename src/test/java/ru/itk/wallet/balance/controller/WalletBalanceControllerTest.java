package ru.itk.wallet.balance.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.itk.wallet.balance.dto.WalletBalanceResponse;
import ru.itk.wallet.balance.service.WalletBalanceService;
import ru.itk.wallet.utils.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(
        controllers = {WalletBalanceController.class}
)
class WalletBalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WalletBalanceService balanceService;

    private WalletBalanceResponse validResponse;

    private static final String BASE_URI = "/api/v1/wallets/";

    @BeforeEach
    void setUpAll() {
        validResponse = new WalletBalanceResponse(
                UUID.randomUUID(),
                new BigDecimal("1234567.89")
        );
    }

    @Test
    void getBalanceReturnsValidDTOAnd200() throws Exception {
        when(balanceService.getBalance(any(UUID.class))).thenReturn(validResponse);

        MvcResult mvcResult = mockMvc.perform(get(new URI(BASE_URI + validResponse.getWalletId().toString())))
                .andExpect(status().isOk())
                .andReturn();

        WalletBalanceResponse result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), WalletBalanceResponse.class);

        assertNotNull(result);
        assertEquals(validResponse.getWalletId(), result.getWalletId());
        assertEquals(validResponse.getBalance(), result.getBalance());
    }

    @Test
    void getBalanceReturns404IfGivenNotValidId() throws Exception {
        when(balanceService.getBalance(any(UUID.class))).thenThrow(new WalletNotFoundException());

        mockMvc.perform(get(new URI(BASE_URI + UUID.randomUUID().toString())))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBalanceReturns400IfValidationFailed() throws Exception {
        mockMvc.perform(get(new URI(BASE_URI + "not-an-UUID")))
                .andExpect(status().isBadRequest());
    }
}