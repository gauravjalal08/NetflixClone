package com.example.apinetflix.Exception;

public class DependencyFailureException extends RuntimeException{

    public DependencyFailureException(Throwable cause){// to get exact cause of failure
        super(cause);
    }
}

