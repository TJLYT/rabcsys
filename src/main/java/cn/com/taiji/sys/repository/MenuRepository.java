package cn.com.taiji.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.com.taiji.domain.Menu;

/**        
 * 类名称：MenuRepository   
 * 类描述：   
 * 创建人：vensi   
 * 创建时间：2017年12月8日 下午9:24:03 
 * @version      
 */ 
@Repository
public interface MenuRepository extends JpaRepository<Menu,String>,JpaSpecificationExecutor<Menu>,PagingAndSortingRepository<Menu, String> {

}
