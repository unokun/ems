package jp.smaphonia.app.employee;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import jp.smaphonia.domain.model.CsvEmployee;
import jp.smaphonia.domain.model.Division;
import jp.smaphonia.domain.model.Employee;
import jp.smaphonia.domain.model.Prefecture;
import jp.smaphonia.domain.service.division.DivisionService;
import jp.smaphonia.domain.service.employee.EmployeeService;
import jp.smaphonia.domain.service.prefecture.PrefectureService;


@Controller
@RequestMapping("employees")
public class EmployeesController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	DivisionService divisionService;

	@Autowired
	PrefectureService prefectureService;

	@RequestMapping(method=RequestMethod.GET)
	String listEmployees(Model model) {
		List<Employee> employees = employeeService.findAllEmployee();
		model.addAttribute("employees", employees);
		model.addAttribute("id", "");
		model.addAttribute("divId", "");
		model.addAttribute("name", "");
		return "employee/listEmployees";
	}
	@RequestMapping(method=RequestMethod.POST, params="edit")
	String edit(@RequestParam("employeeId") String employeeId, Model model) {
		Employee employee = employeeService.findEmployee(employeeId);
		model.addAttribute("employee", employee);
		List<Division> divisions = divisionService.findAllDivision();
		model.addAttribute("divisions", divisions);
		List<Prefecture> prefs = prefectureService.findAllPrefecture();
		model.addAttribute("prefs", prefs);
		
		model.addAttribute("method", "edit");
		return "employee/editEmployee";
	}
	@RequestMapping(method=RequestMethod.POST, params="add")
	String add(Model model) {
		Employee employee = employeeService.newEmployee();
		model.addAttribute("employee", employee);
		List<Division> divisions = divisionService.findAllDivision();
		model.addAttribute("divisions", divisions);
		List<Prefecture> prefs = prefectureService.findAllPrefecture();
		model.addAttribute("prefs", prefs);
		
		model.addAttribute("method", "add");
		return "employee/editEmployee";
	}	
	@RequestMapping(method=RequestMethod.POST, params="update")
	String update(@RequestParam("employeeId") String employeeId, @RequestParam("name") String employeeName,
			@RequestParam("age") String age, @RequestParam("gender") String gender,
			@RequestParam("divId") String divId, @RequestParam("postalCode") String postalCode,
			@RequestParam("pref") String pref, @RequestParam("city") String city,
			@RequestParam("address") String address, Model model) {
		employeeService.update(employeeId, employeeName, divId, Integer.parseInt(age), gender, postalCode, pref, city, address);
		
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
		model.addAttribute("employeeId", employeeId);
		model.addAttribute("divisionId", divisionId);
		model.addAttribute("employeeName", employeeName);

		return "employee/listEmployees";
	}
	//@RequestMappingを付けると画面遷移しない
	//Spring MVC で CSV をダウンロードさせる - Qiita 
	//http://qiita.com/yo1000/items/050c5c47daabf7a10e10
	//Spring Bootで日本語ファイル名のファイルダウンロード - システム開発メモ 
	//http://progmemo.wp.xdomain.jp/archives/869
	//SpringBoot/SpringMVCでファイルのダウンロード(CSV, Excel, PDF, ローカルファイル) - くらげになりたい。 
	//http://wannabe-jellyfish.hatenablog.com/entry/2016/05/20/005755
	@RequestMapping(method=RequestMethod.POST, params="csv_export")
	@ResponseBody
	public ResponseEntity<byte[]> exportCsv(@RequestParam("employeeId") String employeeId, @RequestParam("divisionId") String divisionId, @RequestParam("employeeName") String employeeName, Model model) throws JsonProcessingException, IOException {
		List<CsvEmployee> csvEmployees = new ArrayList<CsvEmployee>();
		List<Employee> employees;
		if (employeeId.isEmpty() && divisionId.isEmpty() && employeeName.isEmpty()) {
			employees = employeeService.findAllEmployee();
		} else {
			employees = employeeService.findEmployee(employeeId, divisionId, employeeName);
		}
		for (Employee employee : employees) {
			csvEmployees.add(new CsvEmployee(employee));
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/csv; charset=MS932");
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    String filename = URLEncoder.encode("社員一覧.csv", StandardCharsets.UTF_8.name());
	    headers.setContentDispositionFormData("filename", filename);

		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(CsvEmployee.class).withHeader();
		return new ResponseEntity<>(mapper.writer(schema).writeValueAsString(csvEmployees).getBytes("MS932"), headers, HttpStatus.OK);
	}	
}
