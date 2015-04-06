/**
 * 
 */
package com.mydev.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
public class HelloWorldController {
	private static final Logger logger = LoggerFactory
			.getLogger(HelloWorldController.class);
	@RequestMapping("/helloWorld")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        logger.debug("hello world");
        return "index";
    }
}
