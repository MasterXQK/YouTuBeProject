package com.Morgan.bilibili.domain.exception;

/**
 * @author Morgan
 * @create 2022-10-17-13:07
 */

public class ConditionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;

    public ConditionException(String code, String name) {
        super(name);
        this.code = code;
    }

    public ConditionException(String name) {
        super(name);
        code = "500";
    }

    // ------------------------------------- get set -------------------------------------------------------------------
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
