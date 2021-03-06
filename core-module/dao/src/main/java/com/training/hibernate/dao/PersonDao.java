package com.training.hibernate.dao;

import com.training.hibernate.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import com.training.hibernate.util.HibernateUtil;

import java.util.*;
import java.io.*;

public class PersonDao {

	public List<Person> getAllPersons(){
        Session session = HibernateUtil.getSessionFactory().openSession();
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
		Session session = HibernateUtil.getSessionFactory().openSession();
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
		Session session = HibernateUtil.getSessionFactory().openSession();
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

	public void addPerson(Person person) {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
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
			session.close();
		}
	}

	public void updatePerson(Person person){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
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
		Session session = HibernateUtil.getSessionFactory().openSession();
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
		Session session = HibernateUtil.getSessionFactory().openSession();
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