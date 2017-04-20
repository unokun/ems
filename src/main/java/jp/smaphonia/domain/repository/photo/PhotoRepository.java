package jp.smaphonia.domain.repository.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.smaphonia.domain.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, String> {
	@Query(value="select max(id) from photo", nativeQuery = true)
	String getMaxId();

}
