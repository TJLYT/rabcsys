package cn.com.taiji.sys.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
public class RoleDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private Date createTime;

	private String creater;

	private String roleDesc;

	private String roleName;

	private int state;

	private Date updateTime;

	private String updater;

	private List<MenuDto> menuDtos;

	//bi-directional many-to-many association to User
	private List<UserDto> userDtos;

	public RoleDto() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdater() {
		return this.updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public List<MenuDto> getMenus() {
		return this.menuDtos;
	}

	public void setMenus(List<MenuDto> menuDtos) {
		this.menuDtos = menuDtos;
	}

	public List<UserDto> getUsers() {
		return this.userDtos;
	}

	public void setUsers(List<UserDto> userDtos) {
		this.userDtos = userDtos;
	}

}