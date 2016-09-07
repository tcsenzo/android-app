package com.senzo.qettal.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventList {

    @JsonProperty("events")
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }
}
