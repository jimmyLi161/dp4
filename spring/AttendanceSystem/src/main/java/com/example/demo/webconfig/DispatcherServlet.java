package com.example.demo.webconfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 调度服务器
 * 解决HTTP Request不能被重复读取的问题
 * @author 贾智云
 * @since 1.0
 * @version 1.0
 * @see org.springframework.web.servlet.DispatcherServlet
 */
@WebServlet("/**")
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet
{
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // 全局替换request对象
        super.service(new BodyReaderHttpServletRequestWrapper(request), response);
    }
}