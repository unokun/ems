package jp.smaphonia.domain.repository.empolyee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import jp.smaphonia.domain.model.Employee;


//Spring Data JPA - Reference Documentation 
//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#specifications

public interface EmployeeRepository extends JpaRepository<Employee,String>, JpaSpecificationExecutor<Employee> {
	@Query(value="select max(id) from employee", nativeQuery = true)
	String getMaxId();
}
