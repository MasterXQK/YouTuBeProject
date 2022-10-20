package com.Morgan.bilibili.api.support;


import com.Morgan.bilibili.domain.exception.ConditionException;
import com.Morgan.bilibili.service.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Morgan
 * @create 2022-10-20-21:20
 */
@Component
public class UserSupport {
    // 拿到前端request里面的token 并且根据JWT解析出userID
    public Long getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        // check userId
        if (userId < 0) {
            throw new ConditionException("400", "非法用户！", "Illegal user!");
        }
        return userId;
    }
}
