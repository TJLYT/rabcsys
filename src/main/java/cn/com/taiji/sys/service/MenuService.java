package cn.com.taiji.sys.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.Menu;
import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.User;
import cn.com.taiji.sys.dto.MenuDto;
import cn.com.taiji.sys.dto.RoleDto;
import cn.com.taiji.sys.dto.UserDto;
import cn.com.taiji.sys.repository.MenuRepository;

/**        
 * 类名称：MenuService   
 * 类描述：   操作Menu对象，将其持久化到数据库，查询修改逻辑删除
 * 创建人：vensi   
 * 创建时间：2017年12月11日 下午1:03:24 
 * @version      
 */ 
@Service
public class MenuService {
	@Autowired
	MenuRepository menuRepo;
	
	@Autowired
	PagenationService pageService;
	/**
	 * @Description: 根据入参menudto，转换为menu对象后，持久化到数据库
	 * @param menuDto
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月10日
	 */
	public void addMenu(MenuDto menuDto) throws IllegalAccessException, InvocationTargetException {
		Menu menu = new Menu();
		
		if(menuDto.getMenu()!=null) {
			Menu menu2 = new Menu();
			BeanUtils.copyProperties(menu2, menuDto.getMenu());
			BeanUtils.copyProperty(menu, "menu", menu2);
		}
		
		BeanUtils.copyProperty(menu, "id", menuDto.getId());
		BeanUtils.copyProperty(menu, "menuName", menuDto.getMenuName());
		BeanUtils.copyProperty(menu, "menuDesc", menuDto.getMenuDesc());
		BeanUtils.copyProperty(menu, "menuUrl", menuDto.getMenuDesc());
		
		if(SecurityContextHolder.getContext()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(menuDto.getCreater()==null&&menuDto.getId().isEmpty()) {
				menu.setId(UUID.randomUUID().toString().replace("-", ""));
				menu.setCreater(userDetails.getUsername());
				Date time = Calendar.getInstance().getTime();
				menu.setCreateTime(time);
			}
		}
		menu.setState(1);
		menuRepo.saveAndFlush(menu);
	}
	
	/**
	 * @Description: 根据传入的menudto对象，转换为menu对象后，设置state=0从逻辑上删除用户
	 * @param roleDto
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月11日
	 */
	@Transactional
	public void deleteMenu(MenuDto menuDto) throws IllegalAccessException, InvocationTargetException {
		Menu menu = new Menu();
		BeanUtils.copyProperties(menu, menuDto);
		menu.setState(0);
		menuRepo.saveAndFlush(menu);
	}
	
	
	/**
	 * @Description: 根据传入的menudto对象，转换为menu对象后更新menu信息
	 * @param roleDto
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月11日
	 */
	@Transactional
	public void updateMenu(MenuDto menuDto) throws IllegalAccessException, InvocationTargetException {
		Menu menu = new Menu();
		MenuDto originMenu = findById(menuDto.getId());
		if(SecurityContextHolder.getContext()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!menuDto.getId().isEmpty()) {
				menu.setUpdater(userDetails.getUsername());
				Date time = Calendar.getInstance().getTime();
				menu.setUpdateTime(time);
				menu.setState(1);
				menu.setMenuName(originMenu.getMenuName());
				menu.setMenuDesc(originMenu.getMenuDesc());
				menu.setMenuUrl(originMenu.getMenuUrl());
			}
		}
		if(menuDto.getMenu()!=null) {
			Menu menu2 = new Menu();
			BeanUtils.copyProperties(menu2, menuDto.getMenu());
			BeanUtils.copyProperty(menu, "menu", menu2);
		}
		BeanUtils.copyProperty(menu, "id", menuDto.getId());
		BeanUtils.copyProperty(menu, "menuName", menuDto.getMenuName());
		BeanUtils.copyProperty(menu, "menuDesc", menuDto.getMenuDesc());
		BeanUtils.copyProperty(menu, "menuUrl", menuDto.getMenuUrl());
		BeanUtils.copyProperty(menu, "creater", menuDto.getCreater());
		BeanUtils.copyProperty(menu, "createTime", menuDto.getCreateTime());
		menuRepo.saveAndFlush(menu);
	}
	
	
	/**
	 * @Description: 根据入参id查询menu信息，并返回menudto对象
	 * @param id
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException RoleDto  
	 * @throws
	 * @author vensi
	 * @date 2017年12月11日
	 */
	public MenuDto findById(String id) throws IllegalAccessException, InvocationTargetException {
		MenuDto menuDto = new MenuDto();
		Menu menu = menuRepo.findOne(id);
		BeanUtils.copyProperty(menuDto, "id", menu.getId());
		BeanUtils.copyProperty(menuDto, "menuName", menu.getMenuName());
		BeanUtils.copyProperty(menuDto, "menuDesc", menu.getMenuDesc());
		BeanUtils.copyProperty(menuDto, "menuUrl", menu.getMenuDesc());
		BeanUtils.copyProperty(menuDto, "creater", menu.getCreater());
		BeanUtils.copyProperty(menuDto, "createTime", menu.getCreateTime());
		BeanUtils.copyProperty(menuDto, "updater", menu.getUpdater());
		BeanUtils.copyProperty(menuDto, "updateTime", menu.getUpdateTime());
		BeanUtils.copyProperty(menuDto, "state", menu.getState());
		return menuDto;
	}
	
	/**
	 * @Description: 简要进行方法说明，并对基础数据类型的参数和返回值加以说明
	 * @return String  
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws
	 * @author vensi
	 * @date 2017年12月11日
	 */
	public Map getPage(int page,int pageSize,
			HashMap<String,String> orderMaps,HashMap<String,String> filters) throws IllegalAccessException, InvocationTargetException {

		Page<Menu> pageContent;
		if (pageSize < 1)pageSize = 1;
		if (pageSize > 100)pageSize = 100;

		List<Order> orders = new ArrayList<Order>();
		if (orderMaps != null) {
			for (String key : orderMaps.keySet()) {
				if ("DESC".equalsIgnoreCase(orderMaps.get(key))) {
					orders.add(new Order(Direction.DESC, key));
				} else {
					orders.add(new Order(Direction.ASC, key));
				}
			}

		}
		PageRequest pageable;
		if (orders.size() > 0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		} else {
			pageable = new PageRequest(page, pageSize);
		}

		if (filters != null) {
			Specification<Menu> spec = new Specification<Menu>() {
				@Override
				public Predicate toPredicate(Root<Menu> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					for (String key : filters.keySet()) {
						String value = filters.get(key);
						if("menuname".equalsIgnoreCase(key)) {
							pl.add(cb.like(root.get(key),value+"%"));
						}
						if("state".equalsIgnoreCase(key)&&!value.isEmpty()) {
							pl.add(cb.equal(root.get(key),value));
						}
					}
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			pageContent = menuRepo.findAll(spec, pageable);
		} else {
			pageContent = menuRepo.findAll(pageable);
		}
		Map map = new HashMap();
		map.put("total", pageContent.getTotalElements());
		map.put("roles", accountPage2Dto(pageContent));
		return map;

	}

	public List<MenuDto> accountPage2Dto(Page<Menu> pageContent) throws IllegalAccessException, InvocationTargetException {
		List<Menu> menus = pageContent.getContent();
		List<MenuDto> menuDtos = new ArrayList<MenuDto>();
		for (Menu menu : menus) {
			MenuDto md = new MenuDto();
			BeanUtils.copyProperty(md, "id", menu.getId());
			BeanUtils.copyProperty(md, "menuName", menu.getMenuName());
			BeanUtils.copyProperty(md, "menuDesc", menu.getMenuDesc());
			BeanUtils.copyProperty(md, "menuUrl", menu.getMenuUrl());
			BeanUtils.copyProperty(md, "creater", menu.getCreater());
			BeanUtils.copyProperty(md, "createTime", menu.getCreateTime());
			BeanUtils.copyProperty(md, "updater", menu.getUpdater());
			BeanUtils.copyProperty(md, "updateTime", menu.getUpdateTime());
			BeanUtils.copyProperty(md, "state", menu.getState());
			menuDtos.add(md);
		}
		return menuDtos;
	}
	
	public Map getMenuPage(String models) throws IllegalAccessException, InvocationTargetException {
		
		Map params = pageService.parseModels(models);
		int page = (Integer) params.get("page");
		int pageSize = (Integer) params.get("pageSize");
		HashMap<String, String> orderMaps =(HashMap) params.get("orderMaps");
		HashMap<String, String> filters =(HashMap) params.get("filters");
		Map map = this.getPage(page,pageSize,orderMaps,filters);
		return map;
		
	}
	
}
