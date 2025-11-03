package ru.itk.wallet.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;

public class ErrorResponseFactory {

    /**
     * Method which is used in order to construct an error response
     * @param status - HTTP status of an exception
     * @param ex - exception which caused the creation of this message
     * @param request - the request in which the exception had been raised
     * @return {@link ErrorResponse} - an RFC 9457 compliant error message wrapped into {@link ResponseEntity} for convenience.
     */
    public static ResponseEntity<ErrorResponse> create(HttpStatus status, Throwable ex, WebRequest request) {
        String path = request.getDescription(false).replaceFirst("^uri=", "");
        String type = (path + '/' +  ex.getClass().getSimpleName().toLowerCase());

        return ResponseEntity
                .status(status)
                .body(
                        ErrorResponse.builder(ex, status, ex.getMessage())
                                .type(URI.create(type))
                                .instance(URI.create(path))
                                .build()
                );
    }
}
