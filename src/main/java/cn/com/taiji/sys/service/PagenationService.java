package cn.com.taiji.sys.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.dialect.PostgreSQL81Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import cn.com.taiji.domain.User;
import cn.com.taiji.sys.dto.UserDto;
import cn.com.taiji.sys.repository.UserRepository;

/**        
 * 类名称：PagenationService   
 * 类描述：   分页查找的分页类
 * 创建人：vensi   
 * 创建时间：2017年12月11日 下午1:47:01 
 * @version      
 */ 
@Service
public class PagenationService {

	@Autowired
	UserRepository userRepo;

	
public Map parseModels(String models) throws IllegalAccessException, InvocationTargetException {
		
		HashMap<String, String> orderMaps = null;
		HashMap<String, String> filters = null;
		
		JSONObject jsonObject = JSONObject.parseObject(models);
		int page = jsonObject.getIntValue("page")-1;//分页起始值
		int pageSize = jsonObject.getIntValue("pageSize");//每页记录条数

		if(jsonObject.getString("sort")!=null) {
			//解析sort数组
			String jsonArray = jsonObject.getString("sort");
			List<HashMap> sortList = JSONObject.parseArray(jsonArray, HashMap.class);
			orderMaps = new HashMap<>();
			for(HashMap<String, String> map : sortList) {
				orderMaps.put(map.get("field"), map.get("dir"));
			}
		}

		if(jsonObject.getJSONObject("filter")!=null) {
			//解析filter过滤条件
			JSONObject jsonObj = jsonObject.getJSONObject("filter");
			String logic = jsonObj.getString("logic");
			//解析filters数组
			List<HashMap> filterList = jsonObj.parseArray(jsonObj.getString("filters"), HashMap.class);
			filters = new HashMap<>();
			filters.put("logic", logic);
			for (HashMap<String,String> map : filterList) {
				filters.put(map.get("field"),map.get("value"));
			}
		}
		
		Map<String,Object> params = new HashMap<>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("orderMaps", orderMaps);
		params.put("filters", filters);
		return params;
		
	}
	

	public Map getUserPage(Map searchParameters) throws IllegalAccessException, InvocationTargetException {
		Map map = new HashMap();
		int page = 0;
		int pageSize = 10;
		Page<User> pageContent;
		if (searchParameters != null && searchParameters.size() > 0
				&& searchParameters.get("page") != null) {
			page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
		}
		if (searchParameters != null && searchParameters.size() > 0
				&& searchParameters.get("pageSize") != null) {
			pageSize = Integer.parseInt(searchParameters.get("pageSize")
					.toString());
		}
		if (pageSize < 1)
			pageSize = 1;
		if (pageSize > 100)
			pageSize = 100;
		List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
		List<Order> orders = new ArrayList<Order>();
		if (orderMaps != null) {
			for (Map m : orderMaps) {
				if (m.get("field") == null)
					continue;
				String field = m.get("field").toString();
				if (!StringUtils.isEmpty(field)) {
					String dir = m.get("dir").toString();
					if ("DESC".equalsIgnoreCase(dir)) {
						orders.add(new Order(Direction.DESC, field));
					} else {
						orders.add(new Order(Direction.ASC, field));
					}
				}
			}
		}
		PageRequest pageable;
		if (orders.size() > 0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		} else {
			Sort s = new Sort(Direction.ASC, "id");
			pageable = new PageRequest(page, pageSize, s);
		}

		pageContent = userRepo.findAll(pageable);
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
}
