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

import cn.com.taiji.domain.Department;
import cn.com.taiji.domain.Employee;
import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.User;
import cn.com.taiji.sys.dto.DepartmentDto;
import cn.com.taiji.sys.dto.EmployeeDto;
import cn.com.taiji.sys.dto.RoleDto;
import cn.com.taiji.sys.dto.UserDto;
import cn.com.taiji.sys.repository.DeptRepository;
import cn.com.taiji.sys.repository.EmpRepository;
import cn.com.taiji.sys.repository.UserRepository;

/**        
 * 类名称：UserService   
 * 类描述：   操作User对象，将其持久化到数据库，查询修改逻辑删除
 * 创建人：vensi   
 * 创建时间：2017年12月8日 下午7:38:24 
 * @version      
 */ 
@Service
public class EmpService {
	@Autowired 
	public EmpRepository emRepo;

	@Autowired
	private DeptRepository deptRepo;
	
	@Autowired 
	public PagenationService pageService;
	/**
	 * @Description: 新增User,入参为UserDto对象,转换为User对象持久化
	 * @param empDto
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月8日
	 */
	public void addEmp(EmployeeDto empDto) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

		Employee employee = new Employee();
		Department dept = new Department();
			if(empDto.getState()==null&&empDto.getId()==null) {
				empDto.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				empDto.setState("1");
			}
		BeanUtils.copyProperties(employee, empDto);

		
		if(empDto.getDepartments()!=null) {
			DepartmentDto dto = empDto.getDepartments().get(0);
			if(dto.getDepartment()!=null) {
				//如果emp的dept有二级部门
				Department parent = new Department();
				BeanUtils.copyProperty(parent,"id", dto.getId());
				BeanUtils.copyProperty(parent,"deptNumber", dto.getDeptNumber());
				BeanUtils.copyProperty(parent,"deptName", dto.getDeptName());
				BeanUtils.copyProperty(parent,"deptDesc", dto.getDeptDesc());
				deptRepo.saveAndFlush(parent);
				
				//先持久化父级部门，再持久化二级部门
				Department child = new Department();
				BeanUtils.copyProperties(child, dto.getDepartment());
				BeanUtils.copyProperty(parent,"department", child);
				BeanUtils.copyProperty(employee,"departments", Arrays.asList(parent));
				deptRepo.saveAndFlush(child);
				
			}else {
				//如果userDto的roles不为空，则将roles中的roleDto转为role
				BeanUtils.copyProperty(dept,"deptNumber", dto.getDeptNumber());
				BeanUtils.copyProperty(dept,"id", dto.getId());
				BeanUtils.copyProperty(dept,"deptName", dto.getDeptName());
				BeanUtils.copyProperty(dept,"deptDesc", dto.getDeptDesc());
				
				BeanUtils.copyProperty(employee,"departments", Arrays.asList(dept));
			}
			
			
			
			//user.setRoles(Arrays.asList(role));
		}
		
		emRepo.saveAndFlush(employee);
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
	public void deleteEmp(EmployeeDto empDto) throws IllegalAccessException, InvocationTargetException {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employee,empDto);
		//User retuser = userRepo.findOne(user.getId());
		employee.setState("0");
		emRepo.saveAndFlush(employee);
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
	public void updateEmp(EmployeeDto empDto) throws IllegalAccessException, InvocationTargetException {
		Employee emp = new Employee();
		EmployeeDto originEmp = findById(empDto.getId());
			if(null!=empDto.getId()&&!empDto.getId().isEmpty()) {
				empDto.setState("1");
			}
		BeanUtils.copyProperties(emp,empDto);
		emRepo.saveAndFlush(emp);
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
	public EmployeeDto findById(String id) throws IllegalAccessException, InvocationTargetException {
		EmployeeDto empDto = new EmployeeDto();
		Employee employee = emRepo.findOne(id);
		BeanUtils.copyProperties(empDto,employee);
		if(employee.getDepartments()!=null) {
			List<DepartmentDto> list = new ArrayList<>();
			//如果user的roles不为空，则将roles中的role转为roleDto
			for(Department dept : employee.getDepartments()) {
				DepartmentDto deptDto = new DepartmentDto();
				
				if(dept.getDepartment()!=null) {
					//如果emp的dept有二级部门
					Department parent = new Department();
					BeanUtils.copyProperty(deptDto,"id", dept.getId());
					BeanUtils.copyProperty(deptDto,"deptNumber", dept.getDeptNumber());
					BeanUtils.copyProperty(deptDto,"deptName", dept.getDeptName());
					BeanUtils.copyProperty(deptDto,"deptDesc", dept.getDeptDesc());
					
					Department child = dept.getDepartment();
					BeanUtils.copyProperty(deptDto,"department", child);
					deptRepo.saveAndFlush(child);
				}else {
					BeanUtils.copyProperties(deptDto, dept);
				}
				
				list.add(deptDto);
			}
			BeanUtils.copyProperty(empDto,"departments",list);
		}
		return empDto;
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

		Page<Employee> pageContent;
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
			Specification<Employee> spec = new Specification<Employee>() {
				@Override
				public Predicate toPredicate(Root<Employee> root,
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
			pageContent = emRepo.findAll(spec, pageable);
		} else {
			pageContent = emRepo.findAll(pageable);
		}
		Map map = new HashMap();
		map.put("total", pageContent.getTotalElements());
		map.put("users", accountPage2Dto(pageContent));
		return map;

	}

	public List<EmployeeDto> accountPage2Dto(Page<Employee> pageContent) throws IllegalAccessException, InvocationTargetException {
		List<Employee> emps = pageContent.getContent();
		List<EmployeeDto> empDtos = new ArrayList<>();
		for (Employee emp : emps) {
			EmployeeDto ed = new EmployeeDto();
			BeanUtils.copyProperties(ed, emp);
				empDtos.add(ed);
		}
		return empDtos;
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
