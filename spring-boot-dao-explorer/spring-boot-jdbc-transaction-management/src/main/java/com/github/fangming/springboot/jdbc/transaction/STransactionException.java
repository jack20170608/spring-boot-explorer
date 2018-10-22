package com.github.fangming.springboot.jdbc.transaction;

public class STransactionException extends RuntimeException {

    public STransactionException(String msg) {
        super(msg);
    }


    public STransactionException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
