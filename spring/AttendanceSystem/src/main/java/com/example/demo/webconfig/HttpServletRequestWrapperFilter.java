package com.example.demo.webconfig;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * HTTP Request包装过滤器
 * 解决HTTP Request不能被重复读取的问题
 * @author 贾智云
 * @since 1.0
 * @version 1.0
 * @see javax.servlet.Filter
 */
@Component
public class HttpServletRequestWrapperFilter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        if (request instanceof HttpServletRequest)
        {
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
        }
        else
            {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy()
    {

    }
}
