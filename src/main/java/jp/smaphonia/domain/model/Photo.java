package jp.smaphonia.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="photo")
public class Photo {
	@Id
	private String id;
	
	private byte[] data;
}
