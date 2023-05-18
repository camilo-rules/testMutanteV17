package com.mutantes.test;

public class ApiResponse {
    private int statusCode;
    private String response;

    public ApiResponse() {
        // Constructor vacío requerido para la deserialización JSON
    }

    public ApiResponse(int statusCode, String response) {
        this.statusCode = statusCode;
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
