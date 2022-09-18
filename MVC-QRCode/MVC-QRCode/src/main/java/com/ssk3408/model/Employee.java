package com.ssk3408.model;

import java.io.InputStream;

public class Employee {
	private Integer id;
	private String name;
	private String department;
	private String dob;
	private byte[] imageQR;
	private byte[] imagePIC;
	private String base64Image;
	private String profile_pic;
	private InputStream profileIS;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Column(name = "qrcode")
	public byte[] getImage() {
		return this.imageQR;
	}
	
	@Column(name = "profile_pic")
	public byte[] getPic() {
		return this.imagePIC;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public String getProfile_pic() {
		return profile_pic;
	}

	public void setProfile_pic(String profilepic) {
		this.profile_pic = profilepic;
	}

	public InputStream getProfileIS() {
		return profileIS;
	}

	public void setProfileIS(InputStream profileIS) {
		this.profileIS = profileIS;
	}

}
