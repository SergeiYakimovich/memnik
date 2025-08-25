package com.memnik.common.config;

import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@WebListener
public class ApplicationRequestContextListener extends RequestContextListener {
}
