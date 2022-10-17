package com.Morgan.bilibili.domain.exception;

/**
 * @author Morgan
 * @create 2022-10-17-13:07
 */

public class ConditionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errMsgEng;
    private String errMsgCn;

    private String code;

    public ConditionException(String code, String name) {
        super(name);
        this.code = code;
    }

    public ConditionException(String name) {
        super(name);
        code = "500";
    }

    public ConditionException(String code, String msgCn, String msgEng) {
        super(msgCn);
        this.code = code;
        this.errMsgEng = msgEng;
        this.errMsgCn = msgCn;
    }
    // ------------------------------------- get set -------------------------------------------------------------------

    public String getErrMsgEng() {
        return errMsgEng;
    }

    public void setErrMsgEng(String errMsgEng) {
        this.errMsgEng = errMsgEng;
    }

    public String getErrMsgCn() {
        return errMsgCn;
    }

    public void setErrMsgCn(String errMsgCn) {
        this.errMsgCn = errMsgCn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
