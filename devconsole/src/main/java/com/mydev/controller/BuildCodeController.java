/**
 * 
 */
package com.mydev.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mydev.entity.TableBean;
import com.mydev.service.BuildCodeService;

/**
 * 
 * 项目名字:mydev<br>
 * 类描述:创建代码<br>
 * 创建人:wengmd<br>
 * 创建时间:2015年3月27日<br>
 * 修改人:<br>
 * 修改时间:2015年3月27日<br>
 * 修改备注:<br>
 * 
 * @version 0.2<br>
 */
@Controller
@RequestMapping("/build-code")
public class BuildCodeController {
	private static final Logger logger = LoggerFactory
			.getLogger(BuildCodeController.class);
	@Autowired
	private BuildCodeService buildCodeService;

	@RequestMapping("/main")
	public String getMainPage(Model model) {
		// model.addAttribute("message", "Hello World!");
		logger.debug("getMainPage");
		return "main-cfg";
	}

	@RequestMapping("/connect")
	public ModelAndView connectDb(String dbtype, String node,
			String username, String password, String keyspace) {
		logger.debug("connectDb");
		logger.debug("dbtype:" + dbtype);
		buildCodeService.connect(node, username, password, keyspace);
		List<TableBean> tables = buildCodeService.getTables();
		ModelAndView modelAndView = new ModelAndView("connect-get-tables");
		modelAndView.addObject("tables", tables);
		return modelAndView;
	}
	
	@RequestMapping("/create_code")
	public ModelAndView createCode(String[] tableNames) {
		logger.debug("create_code");
		ModelAndView modelAndView = null;
		try {
			List<String> lsTableNames = Arrays.asList(tableNames);
			buildCodeService.createCode(lsTableNames);
			
		} catch (Exception e) {
			logger.error("createCode", e);
		}
		return modelAndView;
	}

	public BuildCodeService getBuildCodeService() {
		return buildCodeService;
	}

	public void setBuildCodeService(BuildCodeService buildCodeService) {
		this.buildCodeService = buildCodeService;
	}

}
