package com.senzo.qettal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private String price;
    @JsonProperty("scheduled_date")
    private String scheduledDate;

    /**
     * @deprecated Jackson eyes only
     */
    Event() {
    }

    public Event(String name, String description, String price, String scheduledDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.scheduledDate = scheduledDate;
    }

    @Override
    public String toString() {
        return "Nome: '" + name + '\'' +
                ", Descricao: '" + description + '\'' +
                ", Preço:'" + price + '\'' +
                ", Data:'" + scheduledDate + '\'' +
                '}';
    }
}
