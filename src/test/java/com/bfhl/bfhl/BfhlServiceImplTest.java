package com.bfhl.bfhl;

import com.bfhl.bfhl.dto.RequestDTO;
import com.bfhl.bfhl.dto.ResponseDTO;
import com.bfhl.bfhl.service.BfhlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for BfhlServiceImpl.
 *
 * WHAT ARE UNIT TESTS?
 * Unit tests verify that individual methods/units of your code work correctly.
 * We test the service in isolation — no real HTTP requests, no Spring context needed.
 *
 * @Test: marks a method as a test case — JUnit will run it
 * @BeforeEach: runs before EVERY test to set up fresh state
 * @DisplayName: gives the test a human-readable name in the test report
 *
 * Each test follows the AAA pattern:
 *   Arrange → set up input data
 *   Act     → call the method under test
 *   Assert  → verify the output matches expectations
 */
class BfhlServiceImplTest {

    private BfhlServiceImpl service;

    /**
     * @BeforeEach runs before every single test method.
     * We create a fresh service instance and inject fake property values
     * using ReflectionTestUtils (since @Value doesn't work in plain unit tests).
     */
    @BeforeEach
    void setUp() {
        service = new BfhlServiceImpl();
        // Inject test values for the @Value fields
        ReflectionTestUtils.setField(service, "fullName", "john_doe");
        ReflectionTestUtils.setField(service, "dob", "17091999");
        ReflectionTestUtils.setField(service, "email", "john@xyz.com");
        ReflectionTestUtils.setField(service, "rollNumber", "ABCD123");
    }

    // ──────────────────────────────────────────────────────────
    // TEST GROUP 1: Example A from the task spec
    // Input: ["a", "1", "334", "4", "R", "$"]
    // ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example A: Basic mixed input with numbers, letters, special char")
    void testExampleA_isSuccess() {
        RequestDTO req = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDTO res = service.processData(req);
        assertTrue(res.isSuccess(), "is_success should be true");
    }

    @Test
    @DisplayName("Example A: userId format should be full_name_ddmmyyyy")
    void testExampleA_userId() {
        RequestDTO req = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDTO res = service.processData(req);
        assertEquals("john_doe_17091999", res.getUserId());
    }

    @Test
    @DisplayName("Example A: Odd numbers should be [\"1\"]")
    void testExampleA_oddNumbers() {
        RequestDTO req = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("1"), res.getOddNumbers());
    }

    @Test
    @DisplayName("Example A: Even numbers should be [\"334\", \"4\"]")
    void testExampleA_evenNumbers() {
        RequestDTO req = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("334", "4"), res.getEvenNumbers());
    }

    @Test
    @DisplayName("Example A: Alphabets should be uppercase [\"A\", \"R\"]")
    void testExampleA_alphabets() {
        RequestDTO req = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("A", "R"), res.getAlphabets());
    }

    @Test
    @DisplayName("Example A: Special characters should be [\"$\"]")
    void testExampleA_specialChars() {
        RequestDTO req = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("$"), res.getSpecialCharacters());
    }

    @Test
    @DisplayName("Example A: Sum should be \"339\" (1+334+4)")
    void testExampleA_sum() {
        RequestDTO req = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDTO res = service.processData(req);
        assertEquals("339", res.getSum());
    }

    @Test
    @DisplayName("Example A: concat_string should be \"Ra\" (reversed a,R → R,a → Ra)")
    void testExampleA_concatString() {
        RequestDTO req = new RequestDTO(Arrays.asList("a", "1", "334", "4", "R", "$"));
        ResponseDTO res = service.processData(req);
        assertEquals("Ra", res.getConcatString());
    }

    // ──────────────────────────────────────────────────────────
    // TEST GROUP 2: Example B from the task spec
    // Input: ["2", "a", "y", "4", "&", "-", "*", "5", "92", "b"]
    // ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example B: Odd numbers should be [\"5\"]")
    void testExampleB_oddNumbers() {
        RequestDTO req = new RequestDTO(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b")
        );
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("5"), res.getOddNumbers());
    }

    @Test
    @DisplayName("Example B: Even numbers should be [\"2\", \"4\", \"92\"]")
    void testExampleB_evenNumbers() {
        RequestDTO req = new RequestDTO(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b")
        );
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("2", "4", "92"), res.getEvenNumbers());
    }

    @Test
    @DisplayName("Example B: Alphabets should be [\"A\", \"Y\", \"B\"]")
    void testExampleB_alphabets() {
        RequestDTO req = new RequestDTO(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b")
        );
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("A", "Y", "B"), res.getAlphabets());
    }

    @Test
    @DisplayName("Example B: Special chars should be [\"&\", \"-\", \"*\"]")
    void testExampleB_specialChars() {
        RequestDTO req = new RequestDTO(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b")
        );
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("&", "-", "*"), res.getSpecialCharacters());
    }

    @Test
    @DisplayName("Example B: Sum should be \"103\" (2+4+5+92)")
    void testExampleB_sum() {
        RequestDTO req = new RequestDTO(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b")
        );
        ResponseDTO res = service.processData(req);
        assertEquals("103", res.getSum());
    }

    @Test
    @DisplayName("Example B: concat_string should be \"ByA\"")
    void testExampleB_concatString() {
        RequestDTO req = new RequestDTO(
                Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b")
        );
        ResponseDTO res = service.processData(req);
        assertEquals("ByA", res.getConcatString());
    }

    // ──────────────────────────────────────────────────────────
    // TEST GROUP 3: Example C — multi-char alpha strings
    // Input: ["A", "ABCD", "DOE"]
    // ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Example C: Alphabets should preserve multi-char entries uppercase")
    void testExampleC_alphabets() {
        RequestDTO req = new RequestDTO(Arrays.asList("A", "ABCD", "DOE"));
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("A", "ABCD", "DOE"), res.getAlphabets());
    }

    @Test
    @DisplayName("Example C: Numbers and special chars should be empty lists")
    void testExampleC_emptyNumbersAndSpecial() {
        RequestDTO req = new RequestDTO(Arrays.asList("A", "ABCD", "DOE"));
        ResponseDTO res = service.processData(req);
        assertTrue(res.getOddNumbers().isEmpty());
        assertTrue(res.getEvenNumbers().isEmpty());
        assertTrue(res.getSpecialCharacters().isEmpty());
    }

    @Test
    @DisplayName("Example C: Sum should be \"0\" (no numbers)")
    void testExampleC_sum() {
        RequestDTO req = new RequestDTO(Arrays.asList("A", "ABCD", "DOE"));
        ResponseDTO res = service.processData(req);
        assertEquals("0", res.getSum());
    }

    @Test
    @DisplayName("Example C: concat_string should be \"EoDdCbAa\"")
    void testExampleC_concatString() {
        RequestDTO req = new RequestDTO(Arrays.asList("A", "ABCD", "DOE"));
        ResponseDTO res = service.processData(req);
        assertEquals("EoDdCbAa", res.getConcatString());
    }

    // ──────────────────────────────────────────────────────────
    // TEST GROUP 4: Edge cases
    // ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Edge: Empty data array → all empty lists, sum 0, empty concat")
    void testEmptyArray() {
        RequestDTO req = new RequestDTO(Collections.emptyList());
        ResponseDTO res = service.processData(req);
        assertTrue(res.isSuccess());
        assertTrue(res.getOddNumbers().isEmpty());
        assertTrue(res.getEvenNumbers().isEmpty());
        assertTrue(res.getAlphabets().isEmpty());
        assertTrue(res.getSpecialCharacters().isEmpty());
        assertEquals("0", res.getSum());
        assertEquals("", res.getConcatString());
    }

    @Test
    @DisplayName("Edge: Only special characters")
    void testOnlySpecialChars() {
        RequestDTO req = new RequestDTO(Arrays.asList("@", "#", "!"));
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("@", "#", "!"), res.getSpecialCharacters());
        assertTrue(res.getAlphabets().isEmpty());
        assertEquals("0", res.getSum());
    }

    @Test
    @DisplayName("Edge: Zero should be even")
    void testZeroIsEven() {
        RequestDTO req = new RequestDTO(Arrays.asList("0"));
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("0"), res.getEvenNumbers());
        assertTrue(res.getOddNumbers().isEmpty());
        assertEquals("0", res.getSum());
    }

    @Test
    @DisplayName("Edge: Single alphabet should have alternating caps = uppercase")
    void testSingleAlphabet() {
        RequestDTO req = new RequestDTO(Arrays.asList("z"));
        ResponseDTO res = service.processData(req);
        assertEquals(List.of("Z"), res.getAlphabets());
        assertEquals("Z", res.getConcatString());
    }

    @Test
    @DisplayName("Edge: Numbers returned as strings, not converted to integers")
    void testNumbersAreStrings() {
        RequestDTO req = new RequestDTO(Arrays.asList("7", "100"));
        ResponseDTO res = service.processData(req);
        // The original string values must be preserved exactly
        assertTrue(res.getOddNumbers().contains("7"));
        assertTrue(res.getEvenNumbers().contains("100"));
    }

    @Test
    @DisplayName("Edge: email and roll_number are correctly returned")
    void testEmailAndRollNumber() {
        RequestDTO req = new RequestDTO(Collections.emptyList());
        ResponseDTO res = service.processData(req);
        assertEquals("john@xyz.com", res.getEmail());
        assertEquals("ABCD123", res.getRollNumber());
    }
}