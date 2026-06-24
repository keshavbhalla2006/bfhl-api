package com.bfhl.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * RequestDTO (Data Transfer Object) — represents the JSON body we receive.
 *
 * A DTO is just a plain Java class whose only job is to carry data.
 * When a POST request comes in with JSON like: { "data": ["a", "1", "B"] }
 * Spring automatically maps that JSON into this class (called "deserialization").
 *
 * @JsonProperty("data") tells Jackson (the JSON library) that the JSON field
 * named "data" maps to this Java field.
 */
public class RequestDTO {

    @JsonProperty("data")
    private List<String> data;

    // Default constructor — required by Jackson for deserialization
    public RequestDTO() {}

    public RequestDTO(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}