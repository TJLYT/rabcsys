package cn.com.taiji.sys.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.alibaba.fastjson.JSONObject;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.User;
import cn.com.taiji.sys.dto.RoleDto;
import cn.com.taiji.sys.dto.UserDto;
import cn.com.taiji.sys.repository.RoleRepository;

/**        
 * 类名称：RoleService   
 * 类描述：   操作Role对象，将其持久化到数据库，查询修改逻辑删除
 * 创建人：vensi   
 * 创建时间：2017年12月10日 下午7:03:26 
 * @version      
 */ 
@Service
public class RoleService {
	@Autowired
	public RoleRepository roleRepo;
	
	@Autowired 
	private PagenationService pageService;
	/**
	 * @Description: 根据入参roledto，转换为role对象后，持久化到数据库
	 * @param roleDto
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月10日
	 */
	public void addRole(RoleDto roleDto) throws IllegalAccessException, InvocationTargetException {
		User user = new User();
		Role role = new Role();
		if(SecurityContextHolder.getContext()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(roleDto.getCreater()==null&&roleDto.getId().isEmpty()) {
				roleDto.setId(UUID.randomUUID().toString().replace("-", ""));
				roleDto.setCreater(userDetails.getUsername());
				Date time = Calendar.getInstance().getTime();
				roleDto.setCreateTime(time);
				roleDto.setState(1);
			}
		}
		BeanUtils.copyProperties(role, roleDto);

		if(roleDto.getUsers()!=null) {
			BeanUtils.copyProperties(user, roleDto.getUsers().get(0));
			BeanUtils.copyProperty(user,"users", Arrays.asList(user));
		}

		roleRepo.saveAndFlush(role);
	}
	
	/**
	 * @Description: 根据传入的roledto对象，转换为role对象后，设置state=0从逻辑上删除用户
	 * @param roleDto
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月11日
	 */
	@Transactional
	public void deleteRole(RoleDto roleDto) throws IllegalAccessException, InvocationTargetException {
		Role role = new Role();
		BeanUtils.copyProperties(role,roleDto);
		//User retuser = userRepo.findOne(user.getId());
		role.setState(0);
		roleRepo.saveAndFlush(role);
	}
	
	
	/**
	 * @Description: 根据传入的roledto对象，转换为role对象后更新role信息
	 * @param roleDto
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月11日
	 */
	@Transactional
	public void updateRole(RoleDto roleDto) throws IllegalAccessException, InvocationTargetException {
		Role role = new Role();
		RoleDto originRole = findById(roleDto.getId());
		if(SecurityContextHolder.getContext()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!roleDto.getId().isEmpty()) {
				roleDto.setUpdater(userDetails.getUsername());
				Date time = Calendar.getInstance().getTime();
				roleDto.setUpdateTime(time);
				roleDto.setState(1);
				roleDto.setCreater(originRole.getCreater());
				roleDto.setCreateTime(originRole.getCreateTime());
			}
		}
		BeanUtils.copyProperties(role,roleDto);
		roleRepo.saveAndFlush(role);
	}
	
	
	/**
	 * @Description: 根据入参id查询role信息，并返回roledto对象
	 * @param id
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException RoleDto  
	 * @throws
	 * @author vensi
	 * @date 2017年12月11日
	 */
	public RoleDto findById(String id) throws IllegalAccessException, InvocationTargetException {
		RoleDto roleDto = new RoleDto();
		Role role = roleRepo.findOne(id);
		BeanUtils.copyProperties(roleDto,role);
		
		if(role.getUsers()!=null) {
			List<UserDto> list = new ArrayList<>();
			//如果role的users不为空，则将users中的user转为userDto
			for(User user: role.getUsers()) {
				UserDto userDto = new UserDto();
				BeanUtils.copyProperties(userDto, user);
				System.out.println(userDto.getId());
				list.add(userDto);
			}
			BeanUtils.copyProperty(roleDto,"users",list);
		}
		return roleDto;
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

		Page<Role> pageContent;
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
			Specification<Role> spec = new Specification<Role>() {
				@Override
				public Predicate toPredicate(Root<Role> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					for (String key : filters.keySet()) {
						String value = filters.get(key);
						if("rolename".equalsIgnoreCase(key)) {
							pl.add(cb.like(root.get(key),value+"%"));
						}
						if("state".equalsIgnoreCase(key)&&!value.isEmpty()) {
							pl.add(cb.equal(root.get(key),value));
						}
					}
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			pageContent = roleRepo.findAll(spec, pageable);
		} else {
			pageContent = roleRepo.findAll(pageable);
		}
		Map map = new HashMap();
		map.put("total", pageContent.getTotalElements());
		map.put("roles", accountPage2Dto(pageContent));
		return map;

	}

	public List<RoleDto> accountPage2Dto(Page<Role> pageContent) throws IllegalAccessException, InvocationTargetException {
		List<Role> roles = pageContent.getContent();
		List<RoleDto> roleDtos = new ArrayList<RoleDto>();
		for (Role role : roles) {
			RoleDto rd = new RoleDto();
			BeanUtils.copyProperties(rd, role);
				roleDtos.add(rd);
		}
		return roleDtos;
	}

	public RoleDto roleToDto(Role role) throws IllegalAccessException, InvocationTargetException {
		RoleDto rd = new RoleDto();
		BeanUtils.copyProperties(rd, role);
		return rd;
	}
	
	public Map getRolePage(String models) throws IllegalAccessException, InvocationTargetException {
		
		Map params = pageService.parseModels(models);
		int page = (Integer) params.get("page");
		int pageSize = (Integer) params.get("pageSize");
		HashMap<String, String> orderMaps =(HashMap) params.get("orderMaps");
		HashMap<String, String> filters =(HashMap) params.get("filters");
		Map map = this.getPage(page,pageSize,orderMaps,filters);
		return map;
		
	}
	
}
