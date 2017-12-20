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
import cn.com.taiji.sys.repository.UserRepository;

/**        
 * 类名称：UserService   
 * 类描述：   操作User对象，将其持久化到数据库，查询修改逻辑删除
 * 创建人：vensi   
 * 创建时间：2017年12月8日 下午7:38:24 
 * @version      
 */ 
@Service
public class UserService {
	@Autowired 
	public UserRepository userRepo;

	@Autowired 
	public PagenationService pageService;
	/**
	 * @Description: 新增User,入参为UserDto对象,转换为User对象持久化
	 * @param userDto
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月8日
	 */
	public void addUser(UserDto userDto) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

		User user = new User();
		Role role = new Role();
		if(SecurityContextHolder.getContext()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(userDto.getCreater()==null&&userDto.getId().isEmpty()) {
				userDto.setId(UUID.randomUUID().toString().replace("-", ""));
				userDto.setCreater(userDetails.getUsername());
				Date time = Calendar.getInstance().getTime();
				userDto.setCreateTime(time);
				userDto.setState(1);
			}
		}
		BeanUtils.copyProperties(user, userDto);

		if(userDto.getRoles()!=null) {
			//如果userDto的roles不为空，则将roles中的roleDto转为role
			BeanUtils.copyProperties(role, userDto.getRoles().get(0));
			BeanUtils.copyProperty(user,"roles", Arrays.asList(role));
			//user.setRoles(Arrays.asList(role));
		}

		userRepo.saveAndFlush(user);
	}

	/**
	 * @Description: 根据id逻辑删除用户，将state属性改为0
	 * @param id void  
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws
	 * @author vensi
	 * @date 2017年12月9日
	 */
	@Transactional
	public void deleteUser(UserDto userDto) throws IllegalAccessException, InvocationTargetException {
		User user = new User();
		BeanUtils.copyProperties(user,userDto);
		//User retuser = userRepo.findOne(user.getId());
		user.setState(0);
		userRepo.saveAndFlush(user);
	}

	/**
	 * @Description: 简要进行方法说明，并对基础数据类型的参数和返回值加以说明
	 * @param userDto
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月11日
	 */
	@Transactional
	public void updateUser(UserDto userDto) throws IllegalAccessException, InvocationTargetException {
		User user = new User();
		UserDto originUser = findById(userDto.getId());
		if(SecurityContextHolder.getContext()!=null) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(!userDto.getId().isEmpty()) {
				userDto.setUpdater(userDetails.getUsername());
				Date time = Calendar.getInstance().getTime();
				userDto.setUpdateTime(time);
				userDto.setState(1);
				userDto.setDepartment(originUser.getDepartment());
				userDto.setSecondaryDept(originUser.getSecondaryDept());
				userDto.setUserType(originUser.getUserType());
			}
		}
		BeanUtils.copyProperties(user,userDto);
		userRepo.saveAndFlush(user);
	}

	/**
	 * @Description: 根据userdto对象的Id查询其基本信息，返回值为UserDto对象
	 * @param id
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException UserDto  
	 * @throws
	 * @author vensi
	 * @date 2017年12月8日
	 */
	public UserDto findById(String id) throws IllegalAccessException, InvocationTargetException {
		UserDto userDto = new UserDto();
		User user = userRepo.findOne(id);
		BeanUtils.copyProperties(userDto,user);
		if(user.getRoles()!=null) {
			List<RoleDto> list = new ArrayList<>();
			//如果user的roles不为空，则将roles中的role转为roleDto
			for(Role role : user.getRoles()) {
				RoleDto roleDto = new RoleDto();
				BeanUtils.copyProperties(roleDto, role);
				System.out.println(roleDto.getId());
				list.add(roleDto);
			}
			BeanUtils.copyProperty(userDto,"roles",list);
		}
		return userDto;
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

		Page<User> pageContent;
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
			Specification<User> spec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					for (String key : filters.keySet()) {
						String value = filters.get(key);
						if("username".equalsIgnoreCase(key)) {
							pl.add(cb.like(root.get(key),value+"%"));
						}
						if("state".equalsIgnoreCase(key)&&!value.isEmpty()) {
							pl.add(cb.equal(root.get(key),value));
						}
					}
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			pageContent = userRepo.findAll(spec, pageable);
		} else {
			pageContent = userRepo.findAll(pageable);
		}
		Map map = new HashMap();
		map.put("total", pageContent.getTotalElements());
		map.put("users", accountPage2Dto(pageContent));
		return map;

	}

	public List<UserDto> accountPage2Dto(Page<User> pageContent) throws IllegalAccessException, InvocationTargetException {
		List<User> users = pageContent.getContent();
		List<UserDto> userDtos = new ArrayList<UserDto>();
		for (User user : users) {
			UserDto ud = new UserDto();
			BeanUtils.copyProperties(ud, user);
				userDtos.add(ud);
		}
		return userDtos;
	}

	public UserDto userToDto(User user) throws IllegalAccessException, InvocationTargetException {
		UserDto ud = new UserDto();
		BeanUtils.copyProperties(ud, user);
		return ud;
	}
	
	public Map getUserPage(String models) throws IllegalAccessException, InvocationTargetException {
		Map params = pageService.parseModels(models);
		int page = (Integer) params.get("page");
		int pageSize = (Integer) params.get("pageSize");
		HashMap<String, String> orderMaps =(HashMap) params.get("orderMaps");
		HashMap<String, String> filters =(HashMap) params.get("filters");
		Map map = this.getPage(page,pageSize,orderMaps,filters);
		return map;
	}
}
