package ru.itk.wallet.operation.api;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.ErrorResponse;
import ru.itk.wallet.operation.dto.WalletOperationInformation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itk.wallet.operation.dto.WalletOperationResponse;

/**
 * API definition of operations with a wallet
 */
public interface WalletOperationAPI {

    /**
     * POST(/api/v1/wallet)
     * A method which allows to use the wallet (to make a deposit/a withdrawal)
     * @param walletInfo - information about a wallet, an operation and about the desired amount to deposit/withdraw {@link WalletOperationInformation}
     */
    @PostMapping("/wallet")
    @Operation(
            summary = "Operation with a wallet",
            description = """
                    Used to either make a deposit to the wallet or to withdraw some amount of money.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalletOperationResponse.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            }),
            @ApiResponse(responseCode = "403", description = "Operation forbidden", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            })
    })
    ResponseEntity<WalletOperationResponse> operateOnAWallet(
            @RequestBody(
                    description = "Information about the wallet, operation and amount of that operation",
                    required = true) WalletOperationInformation walletInfo);
}
