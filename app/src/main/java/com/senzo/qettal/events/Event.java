package com.senzo.qettal.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private String image;
    @JsonProperty("original_price")
    private String originalPrice;
    @JsonProperty
    private String price;
    @JsonProperty("scheduled_date")
    private String scheduledDate;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
