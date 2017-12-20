package cn.com.taiji.sys.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import cn.com.taiji.RabcsysApplication;
import cn.com.taiji.sys.dto.RoleDto;
import cn.com.taiji.sys.dto.UserDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=RabcsysApplication.class)
@WebAppConfiguration
public class UserServiceTest {
	@Autowired
	UserService userService;

	/*@Autowired
	private WebApplicationContext webApplicationContext;*/

	/**
	 * @Description: 增加User对象持久化的测试方法 
	 * @throws
	 * @author vensi
	 * @date 2017年12月10日
	 */
	@Test
	public void testAddUser() {

		UserDto userDto = new UserDto();
		userDto.setId("20171213");
		RoleDto roleDto = new RoleDto();
		roleDto.setId("1213");
		userDto.setRoles(Arrays.asList(roleDto));
		try {
			userService.addUser(userDto);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 根据userid,将其逻辑删除，更改其state状态为0的测试方法
	 * @throws
	 * @author vensi
	 * @date 2017年12月10日
	 */
	@Test
	public void testDeleteUser() {
		UserDto userDto = new UserDto();
		userDto.setId("20171212");
		try {
			userService.deleteUser(userDto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:根据UserDto对象的id查找user对象的详细信息的测试方法
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月10日
	 */
	@Test
	public void findUserById() throws IllegalAccessException, InvocationTargetException {
		UserDto userDto = userService.findById("20171209");
		System.out.println(userDto.getRoles().get(0).toString());
	}
}
