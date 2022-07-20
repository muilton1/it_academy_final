package it.academy.user_service.controller.advices;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    private String logref;
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getLogref() {
        return logref;
    }

    public void setLogref(String logref) {
        this.logref = logref;
    }
}
