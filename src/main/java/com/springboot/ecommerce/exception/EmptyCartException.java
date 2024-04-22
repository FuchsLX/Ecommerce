package com.springboot.ecommerce.exception;

import lombok.NoArgsConstructor;

public class EmptyCartException extends RuntimeException{

    public EmptyCartException() {super();}
    public EmptyCartException(String message) {super(message);}
    public EmptyCartException(String message, Throwable cause) {super(message, cause);}
    public EmptyCartException(Throwable cause) {super(cause);}
}
