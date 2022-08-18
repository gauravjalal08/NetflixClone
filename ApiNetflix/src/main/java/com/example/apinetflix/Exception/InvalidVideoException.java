package com.example.apinetflix.Exception;

public class InvalidVideoException  extends RuntimeException{
    public InvalidVideoException(final String message){
        super(message);
    }

}
