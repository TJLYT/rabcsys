package cn.com.taiji.sys.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
public class MenuDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private Date createTime;

	private String creater;

	private String menuDesc;

	private String menuName;

	private String menuUrl;

	private int state;

	private Date updateTime;

	private String updater;

	//bi-directional many-to-one association to Menu
	private MenuDto menuDto;

	//bi-directional many-to-one association to Menu
	private List<MenuDto> menuDtos;

	//bi-directional many-to-many association to Role
	private List<RoleDto> roleDtos;

	public MenuDto() {
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

	public String getMenuDesc() {
		return this.menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
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

	public MenuDto getMenu() {
		return this.menuDto;
	}

	public void setMenu(MenuDto menuDto) {
		this.menuDto = menuDto;
	}

	public List<MenuDto> getMenus() {
		return this.menuDtos;
	}

	public void setMenus(List<MenuDto> menuDtos) {
		this.menuDtos = menuDtos;
	}

	public MenuDto addMenus(MenuDto menuDtos) {
		getMenus().add(menuDtos);
		menuDtos.setMenu(this);

		return menuDtos;
	}

	public MenuDto removeMenus(MenuDto menuDtos) {
		getMenus().remove(menuDtos);
		menuDtos.setMenu(null);

		return menuDtos;
	}

	public List<RoleDto> getRoles() {
		return this.roleDtos;
	}

	public void setRoles(List<RoleDto> roleDtos) {
		this.roleDtos = roleDtos;
	}

}