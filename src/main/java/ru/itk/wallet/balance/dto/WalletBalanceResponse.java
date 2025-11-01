package ru.itk.wallet.balance.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class WalletBalanceResponse {

    // Not @Positive, because I believe that the account can be in the red (e.g. you have 0 and the bank charges you with a yearly account maintenance fee)
    @NotNull
    private BigDecimal walletBalance;
}
