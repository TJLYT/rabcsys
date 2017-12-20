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

import cn.com.taiji.domain.Role;
import cn.com.taiji.sys.dto.RoleDto;
import cn.com.taiji.sys.dto.RoleDto;
import cn.com.taiji.sys.service.RoleService;


@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	ObjectMapper om;
	
	@RequestMapping(value="role_list")
	public String toUserList() {
		return "role_list";
	}
	
	@RequestMapping(value="rolelist")
	@ResponseBody
	public String pagenationTest(String models){

		Map rolesMap = new HashMap<>();
		
		if(models!=null&&models.length()>0) {

			try {
				rolesMap = roleService.getRolePage(models);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return JSONObject.toJSONString(rolesMap);
	}
	
	@RequestMapping(value="roleAdd",method=RequestMethod.GET)
	public String roleAdd(Model model) {
		RoleDto roleDto = new RoleDto();
		model.addAttribute("r", roleDto);
		return "role_modal";
	}
	
	@RequestMapping(value="roleEdit",method=RequestMethod.GET)
	public String roleEdit(String id,Model model) {
		RoleDto roleDto = null;
		try {
			roleDto = roleService.findById(id);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		model.addAttribute("r", roleDto);
		return "role_modal";
	}
	
	
	@RequestMapping(value="addRole",method=RequestMethod.POST)
	@ResponseBody
	public String addRole(String data) throws JsonParseException, JsonMappingException, IOException, InstantiationException, NoSuchMethodException {
		System.out.println(data);
		Map<String, String> result = new HashMap<>();
		if(data!=null) {
			RoleDto roleDto = om.readValue(data, RoleDto.class);
			try {
				if(roleDto.getId()==null||roleDto.getId().isEmpty()) {
					//如果id为null值则为新增角色
					//userDto.setCreateTime(Calendar.getInstance().getTime());
					roleService.addRole(roleDto);
					result.put("msg", "操作成功");
				}else {
					//id不为null或者空,为修改用户信息
					roleService.updateRole(roleDto);
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
	@RequestMapping(value="roleDel",method=RequestMethod.POST)
	@ResponseBody
	public String roleDel(String id) throws JsonProcessingException {
		HashMap<String, String> result = new HashMap<>();
		RoleDto menuDto = null;
		try {
			menuDto = roleService.findById(id);
			roleService.deleteRole(menuDto);
			result.put("msg", "删除成功");
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			result.put("msg", "删除失败");
		}
		 return om.writeValueAsString(result);
	}
	
}
