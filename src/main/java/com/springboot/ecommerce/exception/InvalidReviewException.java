package com.springboot.ecommerce.exception;

public class InvalidReviewException extends RuntimeException{
    public InvalidReviewException(String message) {super(message);}
    public InvalidReviewException(String message, Throwable cause) {super(message, cause);}
    public InvalidReviewException(Throwable cause) {super(cause);}
    public InvalidReviewException() {super();}
}
