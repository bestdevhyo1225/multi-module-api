package com.hyoseok.product.domain.rds.usecase.exception;

public class NotFoundProductException extends RuntimeException {

    public NotFoundProductException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

}
