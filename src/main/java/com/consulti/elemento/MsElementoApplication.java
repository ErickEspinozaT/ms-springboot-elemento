package com.consulti.elemento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.DispatcherServlet;

@ComponentScan({ "com.consulti" })
@SpringBootApplication
public class MsElementoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsElementoApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<DispatcherServlet> dispatcherServletServletRegistrationBean(
			DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean<DispatcherServlet> bean = new ServletRegistrationBean<DispatcherServlet>(dispatcherServlet,
				"/elemento/*");
		bean.setAsyncSupported(true);
		bean.setName("elemento");
		bean.setLoadOnStartup(1);
		return bean;
	}
}
