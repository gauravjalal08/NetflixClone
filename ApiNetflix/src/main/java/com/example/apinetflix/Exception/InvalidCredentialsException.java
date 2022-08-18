package com.example.apinetflix.Exception;

public class InvalidCredentialsException extends RuntimeException{
    private String message; //passing a message
    public InvalidCredentialsException(final String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
