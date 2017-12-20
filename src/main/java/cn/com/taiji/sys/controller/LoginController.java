package cn.com.taiji.sys.controller;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.taiji.sys.service.PagenationService;


/**
 * @author vensi
 *
 */
@Controller
@SuppressWarnings(value="all")
public class LoginController {

	@Autowired
	ObjectMapper om;

	@Autowired 
	PagenationService pageService;

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String toLogin() {
		return "login";
	}

	//权限控制：菜单，方法，页面按钮，页面片段
	@RequestMapping(value="/home",method=RequestMethod.POST)
	public String userLogin(String username,String password) {


		return "home";
	}


	@RequestMapping("/admin/list")
	public String onlyIP() {
		return "admin/list";
	}


}
