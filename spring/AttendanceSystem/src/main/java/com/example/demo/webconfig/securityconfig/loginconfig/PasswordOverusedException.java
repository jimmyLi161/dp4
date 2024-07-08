package com.example.demo.webconfig.securityconfig.loginconfig;

import org.springframework.security.core.AuthenticationException;

/**
 * 密码滥用异常
 * @author 贾智云
 * @since 1.1
 * @version 1.1
 * @see org.springframework.security.core.AuthenticationException
 */
public class PasswordOverusedException extends AuthenticationException
{
    public PasswordOverusedException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public PasswordOverusedException(String msg)
    {
        super(msg);
    }
}
