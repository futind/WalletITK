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

    @NotNull
    @JsonProperty(value = "walletId", required = true)
    private UUID walletId;

    @NotNull
    @JsonProperty(value = "walletId", required = true)
    private BigDecimal balance;
}
