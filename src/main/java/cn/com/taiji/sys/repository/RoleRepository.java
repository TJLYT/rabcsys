package cn.com.taiji.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.User;

/**        
 * 类名称：RoleRepository   
 * 类描述：   
 * 创建人：vensi   
 * 创建时间：2017年12月8日 下午9:17:03 
 * @version      
 */ 
@Repository
public interface RoleRepository extends JpaRepository<Role,String>,JpaSpecificationExecutor<Role>,PagingAndSortingRepository<Role, String> {
	
}
