package jp.smaphonia.app.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.smaphonia.domain.model.Division;
import jp.smaphonia.domain.model.Employee;
import jp.smaphonia.domain.service.division.DivisionService;
import jp.smaphonia.domain.service.employee.EmployeeService;

@Controller
@RequestMapping("employees")
public class EmployeesController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DivisionService divisionService;
	
	@RequestMapping(method=RequestMethod.GET)
	String listEmployees(Model model) {
		List<Employee> employees = employeeService.findAllEmployee();
		model.addAttribute("employees", employees);
		return "employee/listEmployees";
	}
	@RequestMapping(method=RequestMethod.POST, params="edit")
	String edit(@RequestParam("employeeId") String employeeId, Model model) {
		Employee employee = employeeService.findEmployee(employeeId);
		model.addAttribute("employee", employee);
		List<Division> divisions = divisionService.findAllDivision();
		model.addAttribute("divisions", divisions);
		model.addAttribute("method", "edit");
		return "employee/editEmployee";
	}
	@RequestMapping(method=RequestMethod.POST, params="add")
	String add(Model model) {
		Employee employee = employeeService.newEmployee();
		model.addAttribute("employee", employee);
		model.addAttribute("method", "add");
		return "employee/editEmployee";
	}	
	@RequestMapping(method=RequestMethod.POST, params="update")
	String update(@RequestParam("employeeId") String employeeId, @RequestParam("name") String employeeName, @RequestParam("address") String employeeAddress, Model model) {
		employeeService.update(employeeId, employeeName, employeeAddress);
		
		List<Employee> employees = employeeService.findAllEmployee();
		model.addAttribute("employees", employees);
		return "employee/listEmployees";
	}	
	@RequestMapping(method=RequestMethod.POST, params="cancel")
	String cancel(@RequestParam("employeeId") String employeeId, Model model) {
		employeeService.cancel(employeeId);
		
		List<Employee> employees = employeeService.findAllEmployee();
		model.addAttribute("employees", employees);
		return "employee/listEmployees";
	}
	@RequestMapping(method=RequestMethod.POST, params="search")
	String search(Model model) {
		List<Division> divisions = new ArrayList<Division>();
		Division division = new Division();
		division.setId("");
		division.setName("未指定");
		divisions.add(division);
		divisions.addAll(divisionService.findAllDivision());
		
		model.addAttribute("divisions", divisions);
		return "employee/searchEmployee";
	}
	@RequestMapping(method=RequestMethod.POST, params="searchEmployee")
	String searchEmployee(@RequestParam("employeeId") String employeeId, @RequestParam("divisionId") String divisionId, @RequestParam("employeeName") String employeeName, Model model) {
		List<Employee> employees = employeeService.findEmployee(employeeId, divisionId, employeeName);
		model.addAttribute("employees", employees);
		return "employee/listEmployees";
	}
}
