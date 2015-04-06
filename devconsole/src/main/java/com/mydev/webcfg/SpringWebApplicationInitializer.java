/**
 * 
 */
package com.mydev.webcfg;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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
public class SpringWebApplicationInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	  protected Class<?>[] getRootConfigClasses() {
	    return new Class[] { AppConfig.class };
	  }

	  @Override
	  protected Class<?>[] getServletConfigClasses() {
	    return new Class[] { WebConfig.class };
	  }

	  @Override
	  protected String[] getServletMappings() {
	    return new String[] { "/" };
	  }

	  @Override
	  protected Filter[] getServletFilters() {
	    return super.getServletFilters();
	  }


}
