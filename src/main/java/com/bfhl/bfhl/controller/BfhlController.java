package com.bfhl.bfhl.controller;

import com.bfhl.bfhl.dto.RequestDTO;
import com.bfhl.bfhl.dto.ResponseDTO;
import com.bfhl.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * BfhlController — the HTTP layer of our application.
 *
 * This class is responsible for:
 *   - Listening for incoming HTTP requests at specific routes
 *   - Extracting data from the request body
 *   - Calling the service layer to do the actual work
 *   - Returning the HTTP response with the right status code
 *
 * @RestController = @Controller + @ResponseBody
 *   - @Controller: marks this as a Spring MVC controller
 *   - @ResponseBody: automatically serializes return values to JSON
 *
 * @CrossOrigin("*"): allows requests from any domain (important for APIs
 *   that will be tested from browsers or Postman from different origins)
 */
@RestController
@CrossOrigin("*")
public class BfhlController {

    /**
     * @Autowired tells Spring: "Find a bean of type BfhlService and inject it here."
     * Spring will find BfhlServiceImpl (which implements BfhlService) and wire it in.
     * This is called Dependency Injection — we don't create the object ourselves.
     */
    @Autowired
    private BfhlService bfhlService;

    /**
     * POST /bfhl endpoint — the main API route.
     *
     * @PostMapping("/bfhl"): this method handles POST requests to /bfhl
     * @RequestBody RequestDTO request: Spring reads the JSON body and converts
     *   it into a RequestDTO object automatically.
     * ResponseEntity<ResponseDTO>: lets us control both the response body AND
     *   the HTTP status code (200 OK, 400 Bad Request, etc.)
     */
    @PostMapping("/bfhl")
    public ResponseEntity<ResponseDTO> processData(@RequestBody RequestDTO request) {
        // Delegate all logic to the service layer
        ResponseDTO response = bfhlService.processData(request);

        // Return 200 OK with the response body
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}