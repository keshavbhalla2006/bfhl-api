package com.bfhl.bfhl.exception;

import com.bfhl.bfhl.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

/**
 * GlobalExceptionHandler — catches exceptions from any controller and returns
 * a clean, structured JSON error response instead of a raw Java stack trace.
 *
 * @RestControllerAdvice: applies this handler globally to ALL controllers.
 * Without this, an exception would result in an ugly 500 Internal Server Error
 * with a Java stack trace — very bad for an API!
 *
 * Example: if someone sends { "data": null } or malformed JSON,
 * this handler catches it and returns:
 *   { "is_success": false, ... }  with HTTP 400 Bad Request
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles malformed JSON or missing "data" field in request body.
     * E.g., sending plain text instead of JSON, or missing the request body entirely.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handleMalformedRequest(HttpMessageNotReadableException ex) {
        return buildErrorResponse("Invalid request body. Please send valid JSON with a 'data' array.");
    }

    /**
     * Handles NullPointerException — e.g., if data list is null.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDTO> handleNullPointer(NullPointerException ex) {
        return buildErrorResponse("Request data cannot be null. Please provide a 'data' array.");
    }

    /**
     * Catch-all handler for any other unexpected exceptions.
     * Always better to return a clean response than expose internal errors.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleGenericException(Exception ex) {
        return buildErrorResponse("An unexpected error occurred: " + ex.getMessage());
    }

    /**
     * Helper to build a failure ResponseDTO.
     * Sets is_success to false and empty arrays/default values.
     */
    private ResponseEntity<ResponseDTO> buildErrorResponse(String message) {
        ResponseDTO errorResponse = new ResponseDTO();
        errorResponse.setSuccess(false);
        errorResponse.setUserId("error");
        errorResponse.setEmail("");
        errorResponse.setRollNumber("");
        errorResponse.setOddNumbers(new ArrayList<>());
        errorResponse.setEvenNumbers(new ArrayList<>());
        errorResponse.setAlphabets(new ArrayList<>());
        errorResponse.setSpecialCharacters(new ArrayList<>());
        errorResponse.setSum("0");
        errorResponse.setConcatString(message);  // put error message in concat_string field
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}