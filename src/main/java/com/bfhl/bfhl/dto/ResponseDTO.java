package com.bfhl.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * ResponseDTO — represents the JSON body we send back.
 *
 * Spring automatically converts this Java object into JSON (called "serialization").
 * Each @JsonProperty defines the exact key name that will appear in the JSON output.
 *
 * Example output:
 * {
 *   "is_success": true,
 *   "user_id": "john_doe_17091999",
 *   "email": "john@xyz.com",
 *   ...
 * }
 */
public class ResponseDTO {

    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("odd_numbers")
    private List<String> oddNumbers;

    @JsonProperty("even_numbers")
    private List<String> evenNumbers;

    @JsonProperty("alphabets")
    private List<String> alphabets;

    @JsonProperty("special_characters")
    private List<String> specialCharacters;

    @JsonProperty("sum")
    private String sum;

    @JsonProperty("concat_string")
    private String concatString;

    // Default constructor
    public ResponseDTO() {}

    // --- Getters and Setters ---
    // These are required so Spring/Jackson can read and write each field

    public boolean isSuccess() { return isSuccess; }
    public void setSuccess(boolean success) { isSuccess = success; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public List<String> getOddNumbers() { return oddNumbers; }
    public void setOddNumbers(List<String> oddNumbers) { this.oddNumbers = oddNumbers; }

    public List<String> getEvenNumbers() { return evenNumbers; }
    public void setEvenNumbers(List<String> evenNumbers) { this.evenNumbers = evenNumbers; }

    public List<String> getAlphabets() { return alphabets; }
    public void setAlphabets(List<String> alphabets) { this.alphabets = alphabets; }

    public List<String> getSpecialCharacters() { return specialCharacters; }
    public void setSpecialCharacters(List<String> specialCharacters) {
        this.specialCharacters = specialCharacters;
    }

    public String getSum() { return sum; }
    public void setSum(String sum) { this.sum = sum; }

    public String getConcatString() { return concatString; }
    public void setConcatString(String concatString) { this.concatString = concatString; }
}