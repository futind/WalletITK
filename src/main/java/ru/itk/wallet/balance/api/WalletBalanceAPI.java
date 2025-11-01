package ru.itk.wallet.balance.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itk.wallet.utils.dto.ExceptionResponse;
import ru.itk.wallet.balance.dto.WalletBalanceResponse;

import java.util.UUID;

public interface WalletBalanceAPI {

    /**
     * GET(/api/v1/wallets/{wallet_uuid}
     * A method used in order to check the balance of a particular wallet
     * @param walletId - unique identifier ({@link UUID}) of wallet
     * @return {@link WalletBalanceResponse} - information about wallet's balance
     */
    @GetMapping("/wallets/{wallet_uuid}")
    @Operation(
            summary = "Get balance of a given wallet"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance returned successfully", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalletBalanceResponse.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )
            })
    })
    ResponseEntity<WalletBalanceResponse> getBalance(@PathVariable(name = "wallet_uuid") UUID walletId);
}
