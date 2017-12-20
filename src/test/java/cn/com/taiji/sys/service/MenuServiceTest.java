package cn.com.taiji.sys.service;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.taiji.RabcsysApplication;
import cn.com.taiji.sys.dto.MenuDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=RabcsysApplication.class)
public class MenuServiceTest {
	@Autowired
	MenuService mService;
	
	@Test
	public void menuAddTest() {
		MenuDto menuDto = new MenuDto();
		menuDto.setId("20171219");
		menuDto.setMenuName("菜单测试");
		/*MenuDto menuDto2 = null;
		try {
			menuDto2 = mService.findById("20171217");
		} catch (IllegalAccessException | InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			menuDto.setMenu(menuDto2);*/
		try {
			mService.addMenu(menuDto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
