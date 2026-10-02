package io.github.fabiocintra.loan;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    BORROWED("Borrowed"),
    REJECTED("Rejected"),
    RETURNED("Returned"),
    LATE("Late"),
    LATE_RETURN("Late return");

    private String description;

    Status (String description){
        this.description = description;
    }

    @JsonValue
    public String getDescription(){
        return description;
    }

}
