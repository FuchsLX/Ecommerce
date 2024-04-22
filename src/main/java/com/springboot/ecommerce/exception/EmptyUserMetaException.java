package com.springboot.ecommerce.exception;

import lombok.NoArgsConstructor;

public class EmptyUserMetaException extends RuntimeException{

    public EmptyUserMetaException() {super();}
    public EmptyUserMetaException(String message) {super(message);}
    public EmptyUserMetaException(String message, Throwable cause) {super(message, cause);}
    public EmptyUserMetaException(Throwable cause) {super(cause);}
}
