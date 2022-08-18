package com.example.apinetflix.Exception;

public class InvalidProfileException  extends RuntimeException{

    public InvalidProfileException(final String message){
        super(message);
    }
}
