package com.springboot.ecommerce.exception;


import lombok.NoArgsConstructor;

public class QuantityExceededCartException extends RuntimeException{

    public QuantityExceededCartException() {super();}

    public QuantityExceededCartException(String message) {
        super(message);
    }
    public QuantityExceededCartException(String message, Throwable cause) {super(message, cause);}
    public QuantityExceededCartException(Throwable cause) {super(cause);}
}
