package ru.itk.wallet.balance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO, containing wallet balance.
 */
@Getter
@Setter
@AllArgsConstructor
public class WalletBalanceResponse {

    /**
     * Unique identifier of a wallet
     */
    @NotNull
    private UUID walletId;

    // Not @Positive, because I believe that the account (wallet) can be in the red (e.g. you have 0 and the bank charges you with a yearly account maintenance fee)
    /**
     * Amount of money on the account
     */
    @NotNull
    private BigDecimal balance;
}
