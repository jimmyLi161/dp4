package com.example.demo.webconfig.securityconfig.logoutconfig;

import org.springframework.security.core.AuthenticationException;

/**
 * 未登录异常
 * @author 贾智云
 * @since 1.0
 * @version 1.0
 * @see org.springframework.security.core.AuthenticationException
 */
public class NotLoggedInException extends AuthenticationException
{
    public NotLoggedInException(String msg)
    {
        super(msg);
    }

    public NotLoggedInException(String msg, Throwable t)
    {
        super(msg, t);
    }
}
