package cn.com.taiji.sys.service;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.taiji.RabcsysApplication;
import cn.com.taiji.sys.dto.RoleDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=RabcsysApplication.class)
public class RoleServiceTest {
	@Autowired
	public RoleService roleService;
	
	@Test
	public void addRole() {
		RoleDto roleDto = new RoleDto();
		roleDto.setId("1209");
		try {
			roleService.addRole(roleDto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
