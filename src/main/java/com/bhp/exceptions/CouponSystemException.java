package com.bhp.exceptions;

public class CouponSystemException extends Exception{

    public CouponSystemException(String message) {
        super(message);
    }

    public CouponSystemException(ErrorMessage errors) {
        super(errors.getMsg());
    }
}
