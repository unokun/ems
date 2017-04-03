package jp.smaphonia.domain.repository.division;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.smaphonia.domain.model.Division;

public interface DivisionRepository extends JpaRepository<Division,String> {
	@Query(value="select max(id) from division", nativeQuery = true)
	String getMaxId();
}
