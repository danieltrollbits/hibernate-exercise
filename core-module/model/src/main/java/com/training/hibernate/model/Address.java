package com.training.hibernate.model;

public class Address {
	private int id;
	private String street;
	private int houseNo;
	private String barangay;
	private String subdivision;
	private String city;
	private String zipCode;

	public Address(){};

	public Address(String street, int houseNo, String barangay, String subdivision, String city, String zipCode){
		this.street = street;
		this.houseNo = houseNo;
		this.barangay = barangay;
		this.subdivision = subdivision;
		this.city = city;
		this.zipCode = zipCode;
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getStreet(){
		return this.street;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public int getHouseNo(){
		return this.houseNo;
	}

	public void setHouseNo(int houseNo){
		this.houseNo = houseNo;
	}

	public String getBarangay(){
		return this.barangay;
	}

	public void setBarangay(String barangay){
		this.barangay = barangay;
	}

	public String getSubdivision(){
		return this.subdivision;
	}

	public void setSubdivision(String subdivision){
		this.subdivision = subdivision;
	}

	public String getCity(){
		return this.city;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getZipCode(){
		return this.zipCode;
	}

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}
}