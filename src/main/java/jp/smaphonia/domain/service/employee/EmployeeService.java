package jp.smaphonia.domain.service.employee;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jp.smaphonia.domain.model.Employee;
import jp.smaphonia.domain.repository.empolyee.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	javax.persistence.EntityManager entityManager;
	
	public List<Employee> findEmployee(String id, String divId, String name) {
		// native SQL
//		StringBuilder builder = new StringBuilder();
//		builder.append("from Employee ");
//		if (!id.isEmpty() || !divId.isEmpty() || !name.isEmpty()) {
//			builder.append(" where 1 = 1 ");
//		}
//		if (!id.isEmpty()) {
//			builder.append(" and id = :id ");
//		}
//		if (!divId.isEmpty()) {
//			builder.append(" and divId = :divId ");
//		}
//		if (!name.isEmpty()) {
//			builder.append(" and name like :name");
//		}
//		Query query = entityManager.createQuery(builder.toString(), Employee.class);
//		if (!id.isEmpty()) {
//			query.setParameter("id", id);
//		}
//		if (!divId.isEmpty()) {
//			query.setParameter("divId", divId);
//		}
//		if (!name.isEmpty()) {
//			query.setParameter("name", "%" + name + "%");
//		}
//		return query.getResultList();
		// Specification
		return employeeRepository.findAll(
				Specifications
				.where(idContains(id))
				.and(divIdContains(divId))
				.and(nameContains(name))
				);
	}
	//Spring Data JPA でのクエリー実装方法まとめ - Qiita 
	//http://qiita.com/tag1216/items/55742fdb442e5617f727
	public Specification<Employee> idContains(String id) {
		return StringUtils.isEmpty(id) ? null : (root, query, cb) -> {
			return cb.like(root.get("id"), id);
		};
	}
	public Specification<Employee> divIdContains(String divId) {
		return StringUtils.isEmpty(divId) ? null : (root, query, cb) -> {
			return cb.like(root.get("divId"), divId);
		};
	}
	public Specification<Employee> nameContains(String name) {
		return StringUtils.isEmpty(name) ? null : (root, query, cb) -> {
			return cb.like(root.get("name"), "%" + name + "%");
		};
	}
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

	public void update(String id, String name, String divId, int age, String gender, String postalCode, String pref, String city, String address) {
		Employee employee = employeeRepository.findOne(id);
		if (employee == null) {
			employee = new Employee();
			employee.setId(id);
		}
		employee.setName(name);
		employee.setAge(age);
		employee.setGender(gender);
		employee.setPostalCode(postalCode);
		employee.setPref(pref);
		employee.setCity(city);
		employee.setAddress(address);
		employeeRepository.save(employee);
		
	}
	public void cancel(String id) {
		Employee employee = employeeRepository.findOne(id);
		employeeRepository.delete(employee);
	}
}
