package cn.com.taiji.sys.service;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.taiji.common.utils.PinYinUtils;
import cn.com.taiji.domain.Department;
import cn.com.taiji.domain.Employee;
import cn.com.taiji.sys.dto.DepartmentDto;
import cn.com.taiji.sys.dto.EmployeeDto;

@Service
public class InitExcel {

	@Autowired
	private EmpService empService;

	String deptid = "";
	
	String deptid2 = "";
	Map<String, DepartmentDto> child = new HashMap<>();
	List<String> childDept = new ArrayList<>();
	

	/*public static void main(String[] args) throws Exception {
		//   String path = "/home/chixue/下载/智慧云SBU人员通讯录0811.xlsx";
		String path = "/home/chixue/下载/通讯录.xlsx";
		initDept(print(path));
	}*/

	/*@Test
	public void sendQA(){
		String path = "/home/chixue/下载/通讯录.xlsx";
		try {
			print(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/


	/**
	 * @Description: 根据excel的路径地址，将数据导入数据库
	 * @param path
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException void  
	 * @throws
	 * @author vensi
	 * @date 2017年12月20日
	 */
	public void print(String path) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException{
		File file = new File(path);
		XSSFWorkbook xssfWorkbook = null;
		try {
			xssfWorkbook = new XSSFWorkbook(file);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sheet从0开始，为表格序列
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(4);

		int rowstart = xssfSheet.getFirstRowNum();
		int rowEnd = xssfSheet.getLastRowNum();
		DepartmentDto department = new DepartmentDto();
		//row=0是表头，故从row=1开始读
		for(int i=rowstart+1;i<=rowEnd;i++)
		{
			
			EmployeeDto emp = new EmployeeDto();
			XSSFRow row = xssfSheet.getRow(i);
			if(null == row) continue;
			int cellStart = row.getFirstCellNum();
			int cellEnd = row.getLastCellNum();
			
			//遍历所有的行数据
			for(int k=cellStart;k<=cellEnd;k++)
			{
				//获取相应行的单元格，即每行的所有列信息
				XSSFCell cell = row.getCell(k);
				if(null==cell) continue;
				String str="";

				switch (cell.getCellType())
				{
				case HSSFCell.CELL_TYPE_NUMERIC: // 数字
					str=cell.getNumericCellValue()+"";
					//       System.out.print(cell.getNumericCellValue()      + "   ");
					break;
				case HSSFCell.CELL_TYPE_STRING: // 字符串\
					str=cell.getStringCellValue()+"";
					//    System.out.print(cell.getStringCellValue() + "   ");
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					str=cell.getBooleanCellValue()+"";
					System.out.println(cell.getBooleanCellValue()  + "   ");
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					str=cell.getCellFormula()+"";
					//    System.out.print(cell.getCellFormula() + "   ");
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					//    System.out.println(" ");
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					System.out.println(" ");
					break;
				default:
					//   System.out.print("未知类型   ");
					break;
				}
				if(k==1){
					emp.setEmpName(str);
					//设置登录名为姓名的拼音字母表示,pinyin4j
					emp.setLoginName(PinYinUtils.toQuanPin(str));
				}else  if(k==0){
					emp.setPassword(str);
					emp.setEmpNumber(str);
				}else  if(k==3){
					emp.setEmptype(str);
				}else  if(k==4){
					emp.setCity(str);
				}else  if(k==8){
					emp.setJobLevel(str);
				}else  if(k==9){
					emp.setJob(str);
				}else  if(k==7){
					emp.setJobSequence(str);
				}else  if(k==2){
					emp.setGender(str);
				}else  if(k==5){
					if(i==1) {
						deptid = UUID.randomUUID().toString().replaceAll("-","");
						department.setId(deptid);
						department.setDeptNumber("SBU-2");
						department.setDeptName(str);
						department.setDeptDesc(str);
						emp.setDepartments(Arrays.asList(department));
					}
					
				}else  if(k==6){
					//加入二级部门信息
					DepartmentDto departmentDto = new DepartmentDto();
					if(!str.isEmpty()) {
							if(childDept.contains(str)) {
								System.out.println("contains");
							DepartmentDto dd = (DepartmentDto)child.get(str);
							dd.setDepartment(department);
							emp.setDepartments(Arrays.asList(dd));
							}else {
								System.out.println("add");
								childDept.add(str);
								deptid2 = UUID.randomUUID().toString().replaceAll("-","");
								departmentDto.setId(deptid2);
								departmentDto.setDeptName(str);
								departmentDto.setDeptDesc(str);
								departmentDto.setDeptNumber("SBU-2-"+str);
								departmentDto.setDepartment(department);
								child.put(str, departmentDto);
								emp.setDepartments(Arrays.asList(departmentDto));
							}
					}

				}

			}
			System.out.println(emp.getEmpName());
			empService.addEmp(emp);
		}



	}







	/**
	 * 去掉重复
	 * @param list
	 * @return
	 */
	public  List<String> removeDuplicate(List<String> list) {        
		HashSet<String> h = new  HashSet<String>(list);        
		list.clear();        
		list.addAll(h);        
		return list;     
	}  




	public  void initDept(Map deptUserMap){
		Set set = deptUserMap.entrySet();         
		Iterator item = set.iterator();         
		List<DepartmentDto> deptList=new ArrayList();
		//初始化一级目录菜单
		Map parentDept=new HashMap();

		while(item.hasNext()){      
			Map.Entry<String, List> entry1=(Map.Entry<String, List>)item.next();    

			String deptName=entry1.getKey();
			DepartmentDto dto=new DepartmentDto();
			dto.setDeptName(entry1.getKey());

			List list=entry1.getValue();
			Iterator it1 = list.iterator();
			while(it1.hasNext()){
				System.out.println(it1.next());
			}
		}  
	}
}