package cn.com.taiji.sys.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.taiji.sys.dto.MenuDto;
import cn.com.taiji.sys.service.MenuService;


@Controller
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	ObjectMapper om;
	
	@RequestMapping(value="menu_list")
	public String toUserList() {
		return "menu_list";
	}
	
	@RequestMapping(value="menulist")
	@ResponseBody
	public String pagenationTest(String models){

		Map rolesMap = new HashMap<>();
		
		if(models!=null&&models.length()>0) {

			try {
				rolesMap = menuService.getMenuPage(models);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return JSONObject.toJSONString(rolesMap);
	}
	
	@RequestMapping(value="menuAdd",method=RequestMethod.GET)
	public String roleAdd(Model model) {
		MenuDto menuDto = new MenuDto();
		model.addAttribute("m", menuDto);
		return "menu_modal";
	}
	
	@RequestMapping(value="menuEdit",method=RequestMethod.GET)
	public String roleEdit(String id,Model model) {
		MenuDto menuDto = null;
		try {
			menuDto = menuService.findById(id);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		model.addAttribute("m", menuDto);
		return "menu_modal";
	}
	
	
	@RequestMapping(value="addMenu",method=RequestMethod.POST)
	@ResponseBody
	public String addRole(String data) throws JsonParseException, JsonMappingException, IOException, InstantiationException, NoSuchMethodException {
		System.out.println(data);
		Map<String, String> result = new HashMap<>();
		if(data!=null) {
			MenuDto menuDto = om.readValue(data, MenuDto.class);
			try {
				if(menuDto.getId()==null||menuDto.getId().isEmpty()) {
					//如果id为null值则为新增角色
					//userDto.setCreateTime(Calendar.getInstance().getTime());
					menuService.addMenu(menuDto);
					result.put("msg", "操作成功");
				}else {
					//id不为null或者空,为修改用户信息
					menuService.updateMenu(menuDto);
					result.put("msg", "操作成功");
				}
				
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
				result.put("msg", "操作失败");
			}
		}
		return om.writeValueAsString(result);
	}
	
	
	/**
	 * @Description: userList页面del删除按钮的处理
	 * @param id
	 * @return
	 * @throws JsonProcessingException String  
	 * @throws
	 * @author vensi
	 * @date 2017年12月15日
	 */
	@RequestMapping(value="menuDel",method=RequestMethod.POST)
	@ResponseBody
	public String roleDel(String id) throws JsonProcessingException {
		HashMap<String, String> result = new HashMap<>();
		MenuDto menuDto = null;
		try {
			menuDto = menuService.findById(id);
			menuService.deleteMenu(menuDto);
			result.put("msg", "删除成功");
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			result.put("msg", "删除失败");
		}
		 return om.writeValueAsString(result);
	}
	
}
