package com.example.demo.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 上传配置
 * 解决无法向服务器上传文件的问题
 * @author 贾智云
 * @since 1.0
 * @version 1.0
 */
@Configuration
public class UploadConfig
{
    @Bean(name="multipartResolver")
    public MultipartResolver multipartResolver()
    {
        return new CommonsMultipartResolver();
    }
}
