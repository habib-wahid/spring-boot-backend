package com.usb.pss.ipaservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Junaid Khan Pathan
 * @date Jul 17, 2023
 */

@Slf4j
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Exception message - " + ex.getMessage());
        log.error("Method name - " + method.getName());
        for (Object param : params) {
            log.error("Parameter value - " + param);
        }
    }
}
