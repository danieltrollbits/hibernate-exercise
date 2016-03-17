package com.training.hibernate.dao;

import com.training.hibernate.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.cfg.Configuration;
import com.training.hibernate.dao.AddressDao;

import java.util.*;
import java.io.*;

public class PersonDao {
	SessionFactory factory = null;
	AddressDao addressDao = new AddressDao();

	public PersonDao(){
		try{
			this.factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public List<Person> getAllPersons(){
		Session session = factory.openSession();
		Transaction tx = null;
		List<Person> persons = new ArrayList<>(); 
		try{
			tx = session.beginTransaction();
			persons = session.createQuery("from Person").list();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return persons;
	}

	public Person getPersonById(int id) {
		Session session = factory.openSession();
		Transaction tx = null;
		Person person = null;
		try{
			tx = session.beginTransaction();
			String hql = "from Person where id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id",id);
			person = (Person) query.uniqueResult();
		}catch(RuntimeException e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return person;
	}

	public List<Person> getPersonByLastName(String name){
		List<Person> persons = new ArrayList<>();
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = "from Person where last_name = :name";
			Query query = session.createQuery(hql);
			query.setParameter("name",name);
			persons = query.list();
		}catch(RuntimeException e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return persons;
	}

	public Person addPerson(Person person) {
		Transaction transaction = null;
		Session session = factory.openSession();
		try{
			transaction = session.beginTransaction();
			session.save(person);
			session.getTransaction().commit();
		}catch(RuntimeException e){
			if(transaction != null){
				transaction.rollback();
			}
			e.printStackTrace();
		}finally{
			session.flush();
			session.close();
		}
		return person;
	}

	public void updatePersonLastName(int id, String lastName){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Person person = (Person)session.get(Person.class, id);
			person.setLastName( lastName );
			session.update(person);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	public void deletePersonById(int id){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Person person = (Person)session.get(Person.class, id);
			session.delete(person);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
	}

	public void updatePersonContact(int id, Contact contact){
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Person person = (Person)session.get(Person.class, id);
			contact.setPerson(person);
			person.getContacts().add(contact);
			session.update(person);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}	
	}
}