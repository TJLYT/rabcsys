package cn.com.taiji.sys.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
public class EmployeeDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String city;

	private String empName;

	private String empNumber;

	private String emptype;

	private String gender;

	private String job;

	private String jobLevel;

	private String jobSequence;

	private String loginName;

	private String password;

	private String state;

	private List<DepartmentDto> departmentDtos;

	public EmployeeDto() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpNumber() {
		return this.empNumber;
	}

	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}

	public String getEmptype() {
		return this.emptype;
	}

	public void setEmptype(String emptype) {
		this.emptype = emptype;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getJobLevel() {
		return this.jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getJobSequence() {
		return this.jobSequence;
	}

	public void setJobSequence(String jobSequence) {
		this.jobSequence = jobSequence;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<DepartmentDto> getDepartments() {
		return this.departmentDtos;
	}

	public void setDepartments(List<DepartmentDto> departmentDtos) {
		this.departmentDtos = departmentDtos;
	}

}