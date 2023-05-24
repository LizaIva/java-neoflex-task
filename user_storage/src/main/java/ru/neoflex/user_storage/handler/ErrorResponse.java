package ru.neoflex.user_storage.handler;

public class ErrorResponse {

    private int status;
    private String error;

    public ErrorResponse(int status) {
        this.status = status;
    }

    public ErrorResponse(String error) {
        this.error = error;
    }
}
