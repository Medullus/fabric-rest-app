package com.medullus.fabricrestapi.exceptions;

import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import javax.print.attribute.standard.Media;
import java.lang.reflect.Method;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private Logger logger = Logger.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
        logger.error("Exception Message:"+throwable.getMessage());
        logger.error("Method Name:"+method.getName());

    }
}
