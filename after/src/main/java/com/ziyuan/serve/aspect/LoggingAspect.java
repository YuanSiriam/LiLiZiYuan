package com.ziyuan.serve.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class description goes here
 *
 * @ClassName: LoggingAspect
 * @Description:
 */
@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.ziyuan.serve.*.service.*.*(..))")
    public void pointcut(){

    }


    /**
     * 记录日志的切点，切点为"execution(* com.nowcoder.community.service.*.*(..))"
     *                  所有service方法运行之前
     * @param joinPoint
     */

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();


        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();


        if (attributes != null){
            HttpServletRequest request = attributes.getRequest();
            String ip = request.getRemoteHost();
            logger.info(String.format("用户[%s],在[%s],访问了[%s].",ip,now,target));
            return;
        }
        logger.info(String.format("在[%s],访问了[%s].",now,target));
    }

}
