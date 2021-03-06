package com.training.hibernate.dao;

import com.training.hibernate.model.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import com.training.hibernate.util.HibernateUtil;

public class AddressDao {

	public Address addAddress(Address address) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(address);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return address;
	}

	public Address getAddressById(int id){
		Address address = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
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
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.update(address);
			tx.commit();
		}catch(RuntimeException e){
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();	
		}
	}
}