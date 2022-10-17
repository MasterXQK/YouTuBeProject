package com.Morgan.bilibili.domain;

import com.sun.org.apache.xml.internal.security.utils.I18n;

/**
 * @author Morgan
 * @create 2022-10-16-19:43
 */
public class JsonResponse<T> {

    // internationalization response message
    public static class I18nMsg {
        String CnMsg;
        String EngMsg;
        public I18nMsg(String EngMsg, String CnMsg) {
            this.EngMsg = EngMsg;
            this.CnMsg = CnMsg;
        }
    }

    private String code;
    private T data;
    private I18nMsg i18nMsg;

    // --------------------------------------------------  construct ---------------------------------------------------
    public JsonResponse(T data) {
        this.data = data;
        this.code = "0";
        this.i18nMsg = new I18nMsg("success", "成功");
    }

    public JsonResponse(String code, I18nMsg i18nMsg) {
        this.code = code;
        this.i18nMsg = i18nMsg;
    }

    // -------------------------------------------------- response func ------------------------------------------------
    // 无数据 返回成功
    public static JsonResponse<String> success() {
        return new JsonResponse<>(null);
    }

    // 有数据 返回成功
    public static JsonResponse<String> success(String data) {
        return new JsonResponse<>(data);
    }

    // 返回失败
    public static JsonResponse<String> fail() {
        return new JsonResponse<>("1", new I18nMsg("fail", "失败"));
    }

    // 失败原因可以写进i18n
    public static JsonResponse<String> fail(String EngMsg, String CnMsg) {
        return new JsonResponse<>("1", new I18nMsg(EngMsg, CnMsg));
    }



    // -------------------------------------------------- get or set ---------------------------------------------------
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getI18nMsg() {
        return i18nMsg.toString();
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "EngMsg=" + i18nMsg.EngMsg + ", CnMsg=" + i18nMsg.CnMsg +
                '}';
    }

    public void setI18nMsg(I18nMsg i18nMsg) {
        this.i18nMsg = i18nMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
