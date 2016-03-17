package com.training.hibernate.model;

import java.util.*;

public class Person {
	private int id;
	private String lastName;
	private String firstName;
	private String middleName;
	private String fullName;
	private Gender gender;
	private Date birthdate;
	private Address address;
	private boolean employed;
	private float gwa;
	private Set<Contact> contacts;
	private Role role;

	public Person(){};

	public Person(String lastName, String firstName, String middleName, Gender gender, Date birthdate, Address address, boolean employed, float gwa, Set<Contact> contacts, Role role){
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.gender = gender;
		this.birthdate = birthdate;
		this.address = address;
		this.employed = employed;
		this.gwa = gwa;
		this.contacts = contacts;
		this.role = role;
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getLastName(){
		return this.lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getFirstName(){
		return this.firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getMiddleName(){
		return this.middleName;
	}

	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}

	public String getFullName(){
		return this.fullName = this.firstName + " " + this.middleName + " " + this.lastName;
	}

	public Gender getGender(){
		return this.gender;
	}

	public void setGender(Gender gender){
		this.gender = gender;
	}

	public Date getBirthdate(){
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate){
		this.birthdate = birthdate;
	}

	public Address getAddress(){
		return this.address;
	}

	public void setAddress(Address address){
		this.address = address;
	}

	public boolean isEmployed(){
		return this.employed;
	}

	public void setEmployed(boolean employed){
		this.employed = employed;
	}

	public float getGwa(){
		return this.gwa;
	}

	public void setGwa(float gwa){
		this.gwa = gwa;
	}

	public Set<Contact> getContacts(){
		return this.contacts;
	}

	public void setContacts(Set<Contact> contacts){
		this.contacts = contacts;
	}

	public Role getRole(){
		return this.role;
	}

	public void setRole(Role role){
		this.role = role;
	}
}