package com.situ.mall.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/manager")
public class IndexManagerController {

	@RequestMapping(value="/index")
	public String index(){
		return "index";
	}
	
	
}
