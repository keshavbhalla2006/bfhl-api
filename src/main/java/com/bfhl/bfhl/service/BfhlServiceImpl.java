package com.bfhl.bfhl.service;

import com.bfhl.bfhl.dto.RequestDTO;
import com.bfhl.bfhl.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BfhlServiceImpl — implements BfhlService and contains all the business logic.
 *
 * @Service tells Spring: "Register this class as a Spring Bean in the service layer."
 * Spring will automatically inject it wherever BfhlService is needed (@Autowired).
 *
 * @Value injects values from application.properties at startup.
 */
@Service
public class BfhlServiceImpl implements BfhlService {

    // These values are read from application.properties
    // Replace them with YOUR actual details before deploying!
    @Value("${app.user.full-name}")
    private String fullName;         // e.g., "john_doe"

    @Value("${app.user.dob}")
    private String dob;              // e.g., "17091999"

    @Value("${app.user.email}")
    private String email;            // e.g., "john@xyz.com"

    @Value("${app.user.roll-number}")
    private String rollNumber;       // e.g., "ABCD123"

    /**
     * Main processing method — takes the data array, categorizes everything,
     * computes sums and concat string, and builds the response.
     */
    @Override
    public ResponseDTO processData(RequestDTO requestDTO) {

        List<String> data = requestDTO.getData();

        // --- Step 1: Prepare result buckets ---
        List<String> oddNumbers       = new ArrayList<>();
        List<String> evenNumbers      = new ArrayList<>();
        List<String> alphabets        = new ArrayList<>();
        List<String> specialChars     = new ArrayList<>();
        long sum                      = 0;
        StringBuilder allAlphaChars   = new StringBuilder(); // for concat_string

        // --- Step 2: Loop through every item in the input array ---
        for (String item : data) {

            if (isNumber(item)) {
                // It's a number (could be multi-digit like "334")
                long num = Long.parseLong(item);
                sum += num;

                if (num % 2 == 0) {
                    evenNumbers.add(item);   // Even: divisible by 2
                } else {
                    oddNumbers.add(item);    // Odd: not divisible by 2
                }

            } else if (isAlphabetic(item)) {
                // It's purely alphabetic (could be multi-char like "ABCD")
                alphabets.add(item.toUpperCase());  // Store in UPPERCASE

                // Collect each individual character for concat_string
                // We append the whole string's characters
                allAlphaChars.append(item);

            } else {
                // Not a number and not purely alphabetic → special character(s)
                specialChars.add(item);
            }
        }

        // --- Step 3: Build concat_string ---
        // Logic: take all alphabetical characters (individual letters from all
        // alphabetic items), reverse the order, then apply alternating caps
        // starting from the LAST character (which becomes index 0 after reverse).
        //
        // Example C: ["A","ABCD","DOE"]
        //   allAlphaChars = "AABCDDOE" (uppercase of all)
        //   reversed char order = E, O, D, D, C, B, A, A
        //   alternating caps (0=upper,1=lower,2=upper...): E,o,D,d,C,b,A,a
        //   result = "EoDdCbAa"

        String concatString = buildConcatString(allAlphaChars.toString());

        // --- Step 4: Build the response ---
        ResponseDTO response = new ResponseDTO();
        response.setSuccess(true);
        response.setUserId(fullName + "_" + dob);   // e.g., "john_doe_17091999"
        response.setEmail(email);
        response.setRollNumber(rollNumber);
        response.setOddNumbers(oddNumbers);
        response.setEvenNumbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecialCharacters(specialChars);
        response.setSum(String.valueOf(sum));         // sum as string, e.g., "339"
        response.setConcatString(concatString);

        return response;
    }

    // ─────────────────────────────────────────────
    // HELPER METHODS
    // ─────────────────────────────────────────────

    /**
     * Returns true if the entire string is a valid integer (positive or negative).
     * Examples: "1" → true, "334" → true, "a" → false, "$" → false
     */
    private boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if EVERY character in the string is an ASCII letter (a-z, A-Z).
     * Examples: "a" → true, "ABCD" → true, "DOE" → true, "a1" → false, "$" → false
     */
    private boolean isAlphabetic(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isLetter(c)) return false;
        }
        return true;
    }

    /**
     * Builds the alternating-caps reversed concat string.
     *
     * Steps:
     *  1. Take the accumulated alpha string (e.g., from ["a","y","b"] → "ayb")
     *  2. Uppercase the whole thing so we have a uniform base: "AYB"
     *  3. Reverse the characters: "BYA"
     *  4. Apply alternating case starting at index 0 = uppercase:
     *     index 0 → UPPER, index 1 → lower, index 2 → UPPER …
     *     "B" → 'B', "Y" → 'y', "A" → 'A'  →  "ByA"
     *
     * This matches Example B: input alphabets a, y, b → concat_string = "ByA"
     * And Example C: A, ABCD, DOE → concat_string = "EoDdCbAa"
     */
    private String buildConcatString(String allAlpha) {
        if (allAlpha == null || allAlpha.isEmpty()) return "";

        // Step 1: Uppercase everything for a clean base
        String upper = allAlpha.toUpperCase();

        // Step 2: Reverse the character sequence
        String reversed = new StringBuilder(upper).reverse().toString();

        // Step 3: Apply alternating caps
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c)); // even index → uppercase
            } else {
                result.append(Character.toLowerCase(c)); // odd index  → lowercase
            }
        }

        return result.toString();
    }
}