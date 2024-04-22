package com.springboot.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuantityExceededOrderException extends RuntimeException{
    private Long orderId;

    public QuantityExceededOrderException() {super();}

    public QuantityExceededOrderException(String message) {
        super(message);
    }

    public QuantityExceededOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuantityExceededOrderException(Throwable cause) {
        super(cause);
    }
}
