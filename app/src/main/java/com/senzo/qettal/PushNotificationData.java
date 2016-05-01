package com.senzo.qettal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushNotificationData {

    @JsonProperty
    private String title;
    @JsonProperty
    private String message;

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
