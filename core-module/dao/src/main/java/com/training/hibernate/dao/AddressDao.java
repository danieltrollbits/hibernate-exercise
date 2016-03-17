package com.training.hibernate.dao;

import com.training.hibernate.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.cfg.Configuration;

public class AddressDao {
	SessionFactory factory = null;

	public AddressDao(){
		try{
			this.factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public Address addAddress(Address address) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(address);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.flush();
			session.close();
		}
		return address;
	}

	public Address getAddressById(int id){
		Address address = null;
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = "from Address where id = :id";
			Query query = session.createQuery(hql);
			query.setInteger("id",id);
			address = (Address) query.uniqueResult();
			tx.commit();
		}catch(RuntimeException e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return address;
	}

	public void updateAddress(Address address) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.update(address);
			tx.commit();
		}catch(RuntimeException e){
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.flush();
			session.close();	
		}
	}
}