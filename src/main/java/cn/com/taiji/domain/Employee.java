package cn.com.taiji.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String city;

	@Column(name="emp_name")
	private String empName;

	@Column(name="emp_number")
	private String empNumber;

	private String emptype;

	private String gender;

	private String job;

	@Column(name="job_level")
	private String jobLevel;

	@Column(name="job_sequence")
	private String jobSequence;

	@Column(name="login_name")
	private String loginName;

	private String password;

	private String state;

	//bi-directional many-to-many association to Department
	@ManyToMany(cascade=CascadeType.MERGE,fetch=FetchType.LAZY)
	@JoinTable(
		name="employee_department"
		, joinColumns={
			@JoinColumn(name="emp_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="dept_id")
			}
		)
	private List<Department> departments;

	public Employee() {
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

	public List<Department> getDepartments() {
		return this.departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

}