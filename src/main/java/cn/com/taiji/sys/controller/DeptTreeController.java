package cn.com.taiji.sys.controller;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.OMGVMCID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.taiji.domain.Employee;
import cn.com.taiji.sys.service.DeptService;

@Controller
public class DeptTreeController {
	
	@Autowired
	DeptService deptService;
	@Autowired
	ObjectMapper om;
	
	@RequestMapping("/deptTree")
	public String deptTree() {
		return "dept_tree";
	}
	
	@RequestMapping("/getData")
	@ResponseBody
	public String getData() {
		List<String> trees = new ArrayList<>();
		String data = "";
		try {
			data = deptService.findDeptTree("ddc6c2a6b6834cb1966678b516d2e7ff");
			trees.add(data);
			trees.add(deptService.findDeptTree("684ee37dbc564ee1b176dfcb845adfa3"));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return trees.toString();
	}
	
	@RequestMapping("/getEmps")
	@ResponseBody
	public String getEmps(String id) throws JsonProcessingException {
		List<Employee> list = deptService.findById(id);
		return om.writeValueAsString(list);
	}
	
}
