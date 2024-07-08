package com.example.demo.webconfig.securityconfig.authenticationconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限比较属性（用于权限表）
 * 用于比较权限之间的关系
 * 可用关系：> >= = <= <
 * @author 贾智云
 * @since 1.0
 * @version 1.0
 * @see org.springframework.security.access.ConfigAttribute
 */
public class AuthenticationCompareAttribute implements ConfigAttribute
{
    private String attribute;
    private Logger logger = LoggerFactory.getLogger(AuthenticationCompareAttribute.class);
    private static final Set<String> availableAuthenticationCompareAttribute = new HashSet<String>()
    {
        {
            addAll(Arrays.asList(">", ">=", "=", "<=", "<"));
        }
    };
    public AuthenticationCompareAttribute(String attribute)
    {
        setAttribute(attribute);
    }
    public void setAttribute(String attribute)
    {
        if (availableAuthenticationCompareAttribute.contains(attribute))
        {
            this.attribute = attribute;
        }
        else
        {
            logger.error("Invalid authentication compare attribute: " + attribute);
            throw new IllegalArgumentException("Invalid authentication compare attribute");
        }
    }
    @Override
    public String getAttribute()
    {
        return attribute;
    }
}
