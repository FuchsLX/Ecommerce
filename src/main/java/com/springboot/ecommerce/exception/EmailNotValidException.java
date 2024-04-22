package com.springboot.ecommerce.exception;


import lombok.NoArgsConstructor;

public class EmailNotValidException extends RuntimeException{

    public EmailNotValidException() {super();}
    public EmailNotValidException(String message) {super(message);}

    public EmailNotValidException(String message, Throwable cause) {super(message, cause);}
    public EmailNotValidException(Throwable cause) {super(cause);}
}
