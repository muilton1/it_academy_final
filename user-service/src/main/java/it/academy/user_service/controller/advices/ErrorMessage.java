package it.academy.user_service.controller.advices;


public class ErrorMessage {
    private String logref;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String logref, String message) {
        this.logref = logref;
        this.message = message;
    }

    public String getLogref() {
        return logref;
    }

    public void setLogref(String logref) {
        this.logref = logref;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
