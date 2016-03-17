package com.training.hibernate.model;

public class Contact {
	private int id;
	private Type type;
	private String value;
	private Person person;

	public Contact(){};

	public Contact(Type type, String value){
		this.type = type;
		this.value = value;
	}

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public Type getType(){
		return this.type;
	}

	public void setType(Type type){
		this.type = type;
	}

	public String getValue(){
		return this.value;
	}

	public void setValue(String value){
		this.value = value;
	}

	public Person getPerson(){
		return this.person;
	}

	public void setPerson(Person person){
		this.person = person;
	}	
}