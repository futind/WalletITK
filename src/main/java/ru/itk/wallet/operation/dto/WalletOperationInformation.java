package ru.itk.wallet.operation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.itk.wallet.operation.dto.enumerations.OperationType;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO class containing the information needed to use the digital wallet.
 */
@Getter
@Setter
@AllArgsConstructor
public class WalletOperationInformation {

    /**
     * Unique identifier for the wallet (UUID)
     */
    @NotNull
    @JsonProperty(value = "walletId", required = true)
    private UUID walletId;

    /**
     * Type of operation
     */
    @NotNull
    @JsonProperty(value = "operationType", required = true)
    private OperationType operationType;

    /**
     * Amount of money to operate on the wallet with (must be positive)
     */
    @NotNull
    @Positive
    @JsonProperty(value = "amount", required = true)
    private BigDecimal amount;
}
