package cn.com.taiji.sys.dto;

import java.io.Serializable;
import java.util.List;

public class DepartmentDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String deptDesc;

	private String deptName;

	private String deptNumber;

	private DepartmentDto departmentDto;

	private List<DepartmentDto> departmentDtos;

	private List<EmployeeDto> employeeDtos;

	public DepartmentDto() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptDesc() {
		return this.deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptNumber() {
		return this.deptNumber;
	}

	public void setDeptNumber(String deptNumber) {
		this.deptNumber = deptNumber;
	}

	public DepartmentDto getDepartment() {
		return this.departmentDto;
	}

	public void setDepartment(DepartmentDto departmentDto) {
		this.departmentDto = departmentDto;
	}

	public List<DepartmentDto> getDepartments() {
		return this.departmentDtos;
	}

	public void setDepartments(List<DepartmentDto> departmentDtos) {
		this.departmentDtos = departmentDtos;
	}

	public DepartmentDto addDepartment(DepartmentDto departmentDto) {
		getDepartments().add(departmentDto);
		departmentDto.setDepartment(this);

		return departmentDto;
	}

	public DepartmentDto removeDepartment(DepartmentDto departmentDto) {
		getDepartments().remove(departmentDto);
		departmentDto.setDepartment(null);

		return departmentDto;
	}

	public List<EmployeeDto> getEmployees() {
		return this.employeeDtos;
	}

	public void setEmployees(List<EmployeeDto> employeeDtos) {
		this.employeeDtos = employeeDtos;
	}

}