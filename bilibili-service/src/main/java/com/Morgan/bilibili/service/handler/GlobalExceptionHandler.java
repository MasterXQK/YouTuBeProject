package com.Morgan.bilibili.service.handler;

import com.Morgan.bilibili.domain.JsonResponse;
import com.Morgan.bilibili.domain.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Morgan
 * @create 2022-10-17-13:06
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE) // 优先级
public class GlobalExceptionHandler {

    // 异常返回前端
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> CommonExceptionHandler(HttpServletRequest r, Exception e) {
        String errMsg = e.getMessage();
        if (e instanceof ConditionException) {
            String errCode = ((ConditionException) e).getCode();
            return new JsonResponse<>(errCode, new JsonResponse.I18nMsg(errMsg, "条件异常"));
        } else {
            return new JsonResponse<>("500", new JsonResponse.I18nMsg(errMsg, errMsg));
        }
    }
}
