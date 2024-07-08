package com.example.demo.webconfig;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * HTTP 请求暂存器
 * 便于获取当前线程的HTTP 请求
 * @author 贾智云
 * @since 1.0
 * @version 1.1
 * 添加用户名和密码的暂存
 */
@Component
public class RequestHolder implements Filter
{

    private static ThreadLocal<HttpServletRequest> httpServletRequestHolder = new ThreadLocal<>();
    public static ThreadLocal<String> username = new ThreadLocal<>();
    public static ThreadLocal<String> password = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException
    {
        httpServletRequestHolder.set((HttpServletRequest) request); // 绑定到当前线程
        try
        {
            chain.doFilter(request, response);
        } catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public void destroy()
    {
    }

    public static HttpServletRequest getHttpServletRequest()
    {
        return httpServletRequestHolder.get();
    }

}