package jp.smaphonia.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//Spring MVC で CSV をダウンロードさせる - Qiita 
//http://qiita.com/yo1000/items/050c5c47daabf7a10e10

@JsonPropertyOrder({"社員番号", "名前", "部署名", "年齢", "性別", "住所"})
public class CsvEmployee {
	@JsonProperty("社員番号")
	private String id;
	
	@JsonProperty("名前")
	private String name;
	
	@JsonProperty("部署名")
	private String divName;
	
	@JsonProperty("年齢")
	private int age;
	
	@JsonProperty("性別")
	private String gender;

	@JsonProperty("住所")
	private String address;
	
	CsvEmployee() {
		
	}
	public CsvEmployee(Employee employee) {
		setId(employee.getId());
		setName(employee.getName());
		setDivName(employee.getDivision().getName());
		setAge(employee.getAge());
		setGender(employee.getGender());
		StringBuilder builder = new StringBuilder();
		builder.append("〒")
			   .append(employee.getPostalCode())
			   .append(" ")
			   .append(employee.getPref())
			   .append(employee.getCity())
			   .append(employee.getAddress());
		setAddress(builder.toString());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDivName() {
		return divName;
	}
	public void setDivName(String divName) {
		this.divName = divName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
