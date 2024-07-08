package com.example.demo.webconfig.securityconfig.authenticationconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 决策属性（用于权限表）
 * 用于处理权限之间的关系
 * 支持的关系：AND（满足所有） OR（满足其中一个） DYNAMIC（动态判断）
 * @author 贾智云
 * @since 1.0
 * @version 1.0
 * @see org.springframework.security.access.ConfigAttribute
 */
public class DecisionAttribute implements ConfigAttribute
{
    private String attribute;
    private Logger logger = LoggerFactory.getLogger(DecisionAttribute.class);
    private static final Set<String> availableDecisionAttributes = new HashSet<String>()
    {
        {
            addAll(Arrays.asList("AND", "OR", "DYNAMIC"));
        }
    };
    public DecisionAttribute(String attribute)
    {
        setAttribute(attribute);
    }
    public void setAttribute(String attribute)
    {
        if (availableDecisionAttributes.contains(attribute))
        {
            this.attribute = attribute;
        }
        else
        {
            logger.error("Invalid decision attribute: " + attribute);
            throw new IllegalArgumentException("Invalid decision attribute");
        }
    }
    @Override
    public String getAttribute()
    {
        return attribute;
    }
}
