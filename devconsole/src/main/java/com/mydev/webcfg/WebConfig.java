/**
 * 
 */
package com.mydev.webcfg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 
 * 项目名字:mydev<br>
 * 类描述:<br>
 * 创建人:wengmd<br>
 * 创建时间:2015年3月26日<br>
 * 修改人:<br>
 * 修改时间:2015年3月26日<br>
 * 修改备注:<br>
 * 
 * @version 0.2<br>
 */
@EnableWebMvc
@Configuration
@ComponentScan(value = "com.mydev.*")
public class WebConfig extends WebMvcConfigurerAdapter {
	final static public String CONTENTTYPE = "text/html; charset=utf-8";
	
	 private static final Logger logger = LoggerFactory
				.getLogger(WebConfig.class);
		@Bean
		public ViewResolver viewResolver() {
			// logger.info("ViewResolver");
			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
			viewResolver.setPrefix("/pages/jsp/");
			viewResolver.setSuffix(".jsp");
			viewResolver.setContentType(CONTENTTYPE);
			return viewResolver;
		}
//		@Bean
//		public ViewResolver freeMarkerViewResolver() {
//			// logger.info("ViewResolver");
//			FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
//			viewResolver.setSuffix("/pages/ftl");
//			viewResolver.setSuffix(".ftl");
//			viewResolver.setContentType(CONTENTTYPE);
//			return viewResolver;
//		}
		//映射资源文件地址
		@Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/assets/**").addResourceLocations("/pages/res/");
	    }

		/**
		 * 描述 : <文件上传处理器>. <br>
		 * <p>
		 * <使用方法说明>
		 * </p>
		 * 
		 * @return
		 */
		@Bean(name = "multipartResolver")
		public CommonsMultipartResolver commonsMultipartResolver() {
			logger.info("CommonsMultipartResolver");
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
			multipartResolver.setMaxUploadSize(5*100*1024*1024);
			multipartResolver.setMaxInMemorySize(5*10*1024*1024);
			return multipartResolver;
		}
}
