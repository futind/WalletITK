package ru.itk.wallet.operation.api;

import ru.itk.wallet.utils.dto.ExceptionResponse;
import ru.itk.wallet.operation.dto.WalletOperationInformation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

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
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "403", description = "Operation forbidden", content = {
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
    ResponseEntity<Void> operateOnAWallet(WalletOperationInformation walletInfo);
}
