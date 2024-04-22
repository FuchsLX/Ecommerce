package com.springboot.ecommerce.exception;


import lombok.NoArgsConstructor;

public class EmailAlreadyTakenException extends RuntimeException{

    public EmailAlreadyTakenException() {super();}

    public EmailAlreadyTakenException(String message) {super(message);}

    public EmailAlreadyTakenException(String message, Throwable cause) {super(message, cause);}

    public EmailAlreadyTakenException(Throwable cause) {super(cause);}
}
