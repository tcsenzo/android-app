package com.senzo.qettal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventList {

    @JsonProperty("events")
    private List<Event> events;

    /**
     * @deprecated Use the factory method
     */
    private EventList() {
    }

    public List<Event> getEvents() {
        return events;
    }
}
