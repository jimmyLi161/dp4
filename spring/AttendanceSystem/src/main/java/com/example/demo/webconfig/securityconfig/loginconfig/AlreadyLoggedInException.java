package com.example.demo.webconfig.securityconfig.loginconfig;

import org.springframework.security.core.AuthenticationException;

/**
 * 已登录异常
 * @author 贾智云
 * @since 1.0
 * @version 1.0
 * @see org.springframework.security.core.AuthenticationException
 */
public class AlreadyLoggedInException extends AuthenticationException
{
    public AlreadyLoggedInException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public AlreadyLoggedInException(String msg)
    {
        super(msg);
    }
}
