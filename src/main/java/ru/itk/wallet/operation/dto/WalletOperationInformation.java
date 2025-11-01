package ru.itk.wallet.operation.dto;

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

    @NotNull
    private UUID walletId;

    @NotNull
    private OperationType operationType;

    @NotNull
    @Positive
    private BigDecimal amount;
}
