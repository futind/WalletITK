package ru.itk.wallet.utils.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * A class which describes an intercepted exception which happened on a server
 */
public class ExceptionResponse {

    /**
     * Time of interception of the exception
     */
    @NotNull
    private LocalDateTime timestamp;

    /**
     * Message which describes the exception
     */
    @NotBlank
    private String message;

    @NotNull
    private HttpStatus status;

    /**
     * Uri of a request
     */
    @NotBlank
    private String path;
}
