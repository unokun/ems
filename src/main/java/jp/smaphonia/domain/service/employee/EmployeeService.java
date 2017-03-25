package jp.smaphonia.domain.service.employee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import jp.smaphonia.domain.model.Employee;
import jp.smaphonia.domain.repository.empolyee.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee findEmployee(String id) {
		return employeeRepository.findOne(id);
	}
	
	public List<Employee> findAllEmployee() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.ASC, "id"));
		return employeeRepository.findAll(new Sort(orders));
	}
	public Employee newEmployee() {
		String maxId = employeeRepository.getMaxId();
		int index = Integer.parseInt(maxId.substring("EMP".length())) + 1;
		String newId = "EMP" + String.format("%04d", index);
		Employee employee = new Employee();
		employee.setId(newId);
		return employee;
	}

	public void update(String id, String name, String address) {
		Employee employee = employeeRepository.findOne(id);
		if (employee == null) {
			employee = new Employee();
			employee.setId(id);
		}
		employee.setName(name);
		employee.setAddress(address);
		employeeRepository.save(employee);
		
	}
	public void cancel(String id) {
		Employee employee = employeeRepository.findOne(id);
		employeeRepository.delete(employee);
	}
}
