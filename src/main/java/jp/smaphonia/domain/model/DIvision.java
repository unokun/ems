package jp.smaphonia.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="division")
public class DIvision {

	@Id
	private String id;
	
	private String name;
}
