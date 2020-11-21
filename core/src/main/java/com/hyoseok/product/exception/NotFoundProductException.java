package com.hyoseok.product.exception;

public class NotFoundProductException extends RuntimeException {

    public NotFoundProductException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

}
