package com.cjdjyf.newssm.aop;

import com.cjdjyf.newssm.base.ResultBean;
import org.apache.shiro.authc.AuthenticationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : cjd
 * @description : AOP拦截返回为ResultBean的类
 * @date : 2018/4/24 11:18
 */
public class ControllerAOP {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();
        ResultBean<?> result;
        try {
            result = (ResultBean<?>) pjp.proceed();
            logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            e.printStackTrace();
            result = handlerException(pjp, e);
        }
        return result;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<String> result = new ResultBean();
        if (e instanceof AuthenticationException){
            result.setData("登录失败！");
            result.setCode(ResultBean.NO_LOGIN);
        }else{
            logger.error(pjp.getSignature() + " error ", e);
            result.setData(e.toString());
            result.setCode(ResultBean.FAIL);
        }
        return result;
    }
}