package com.xinyuow.frame.aspect;

import com.xinyuow.frame.common.constant.InterfaceConstant;
import com.xinyuow.frame.common.exception.InterfaceException;
import com.xinyuow.frame.common.exception.RESPONSE_CODE_ENUM;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 权限控制切面
 *
 * @author mxy
 * @date 2020/11/18
 */
@Slf4j
@Aspect
@Order(1)
@Component
@DependsOn("interfaceConstant")
public class PermissionControlAspect {

    /**
     * 定义切入点，切入点为[com.xinyuow.frame.controller]包以及子包下的所有类中的方法
     */
    @Pointcut("execution(public * com.xinyuow.frame.controller..*.*(..))")
    public void permissionControlPoint() {
    }

    @Around("permissionControlPoint()")
    public Object permissionControl(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        // 获取Token信息
        String token = request.getHeader(InterfaceConstant.AUTH_PARAM_KEY);

        // 判断Token值是否为空
        if (StringUtils.isBlank(token)) {
            log.error("\r\n ********* 请求方法【{}】时，Token值为空 *********", joinPoint.getSignature().getName());
            throw new InterfaceException(RESPONSE_CODE_ENUM.INSUFFICIENT_PERMISSIONS_ERROR);
        }

        // TODO 判断Token是否有对应的权限

        // 执行目标方法
        return joinPoint.proceed();
    }
}
