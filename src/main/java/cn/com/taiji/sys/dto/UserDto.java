package cn.com.taiji.sys.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private int age;

	private Date birthdate;

	private String city;

	private Date createTime;

	private String creater;

	private String department;

	private String education;

	private String email;

	private String gender;

	private byte[] headshot;

	private Date hiredate;

	private String password;

	private String phone;

	private String secondaryDept;

	private int state;

	private String station;

	private String stationLevel;

	private String stationSequence;

	private Date updateTime;

	private String updater;

	private String userName;

	private String userNumber;

	private String userType;

	//bi-directional many-to-many association to Role
	private List<RoleDto> roleDtos;

	public UserDto() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public byte[] getHeadshot() {
		return this.headshot;
	}

	public void setHeadshot(byte[] headshot) {
		this.headshot = headshot;
	}

	public Date getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSecondaryDept() {
		return this.secondaryDept;
	}

	public void setSecondaryDept(String secondaryDept) {
		this.secondaryDept = secondaryDept;
	}

	public int getState() {
		return this.state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStationLevel() {
		return this.stationLevel;
	}

	public void setStationLevel(String stationLevel) {
		this.stationLevel = stationLevel;
	}

	public String getStationSequence() {
		return this.stationSequence;
	}

	public void setStationSequence(String stationSequence) {
		this.stationSequence = stationSequence;
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

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNumber() {
		return this.userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<RoleDto> getRoles() {
		return this.roleDtos;
	}

	public void setRoles(List<RoleDto> roleDtos) {
		this.roleDtos = roleDtos;
	}

}