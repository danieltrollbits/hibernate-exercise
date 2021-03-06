package com.training.hibernate.services;

import com.training.hibernate.model.*;
import com.training.hibernate.dao.*;
import java.util.*;

public class ManagePersonService {
	private PersonDao p = new PersonDao();
	private AddressDao a = new AddressDao();
	private ContactDao c = new ContactDao();

	public List<Person> getAllPersons(){
		return p.getAllPersons();
	}

	public void addPerson(Person person){
		Address address = a.addAddress(person.getAddress());
		person.setAddress(address);
		p.addPerson(person);
	}

	public Person getPersonById(int id) {
		return p.getPersonById(id);
	}

	public List<Person> getPersonByLastName(String name){
		return p.getPersonByLastName(name);
	}

	public void updatePerson(Person person){
		p.updatePerson(person);
	}

	public void deletePersonById(int id){
		Person person = p.getPersonById(id);
		p.deletePersonById(id);
	}

	public Address getAddressById(int id){
		return a.getAddressById(id);
	}

	public void updateAddress(Address address){
		a.updateAddress(address);
	}

	public Set<Contact> getContactsByPersonId(int id){
		return c.getContactsByPersonId(id);
	}

	public void updatePersonContact(int id, Contact contact){
		p.updatePersonContact(id, contact);
	}

	public void deleteContactById(int id){
		c.deleteContactById(id);
	}

}