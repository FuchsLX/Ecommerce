package com.springboot.ecommerce.exception;


import lombok.NoArgsConstructor;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException() {super();}
    public ProductNotFoundException(String message) {super(message);}

    public ProductNotFoundException(String message, Throwable cause) {super(message, cause);}

    public ProductNotFoundException(Throwable cause) {super(cause);}
}
