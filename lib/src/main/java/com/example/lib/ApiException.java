package com.example.lib;

/*
 * Non-specific exception for any kind of issue
 * preventing an API call from doing it's job
 * (e.g. no internet)
 */
public class ApiException extends Exception {
    public ApiException(String errorMessage) {
        super(errorMessage);
    }
}
