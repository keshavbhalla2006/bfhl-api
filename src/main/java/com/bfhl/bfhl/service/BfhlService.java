package com.bfhl.bfhl.service;

import com.bfhl.bfhl.dto.RequestDTO;
import com.bfhl.bfhl.dto.ResponseDTO;

/**
 * BfhlService — the Service Layer Interface.
 *
 * WHY AN INTERFACE?
 * The task explicitly requires an interface for the service layer.
 * This is a best practice called "programming to an interface":
 *
 *   1. LOOSE COUPLING: The Controller only knows about BfhlService (the contract),
 *      not about BfhlServiceImpl (the actual implementation). This means you can
 *      swap out the implementation without touching the controller.
 *
 *   2. TESTABILITY: In tests, you can replace the real implementation with a
 *      mock (fake) version that returns controlled data.
 *
 *   3. CLEAN ARCHITECTURE: Controller → Service (interface) → ServiceImpl (logic)
 *      Each layer only talks to the layer next to it through a contract.
 */
public interface BfhlService {

    /**
     * Processes the input array and returns a categorized response.
     *
     * @param requestDTO the incoming request containing the data array
     * @return ResponseDTO with all categorized results
     */
    ResponseDTO processData(RequestDTO requestDTO);
}