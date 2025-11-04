package ru.itk.wallet.operation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class WalletOperationResponse {

    /**
     * Unique identifier for the wallet (UUID)
     */
    @NotNull
    @JsonProperty(value = "walletId", required = true)
    private UUID walletId;

    /**
     * Amount of money to on account
     */
    @NotNull
    @JsonProperty(value = "balance", required = true)
    private BigDecimal balance;
}
