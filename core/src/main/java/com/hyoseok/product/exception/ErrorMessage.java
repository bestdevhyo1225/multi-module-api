package com.hyoseok.product.exception;

public enum ErrorMessage {

    NOT_FOUND_PRODUCT_IN_DATABASE("Database에 해당 상품이 존재하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
