package com.infotech.client;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.infotech.entities.Employee;
import com.infotech.util.HibernateUtil;

public class InsertRecordClientTest {

	public static void main(String[] args) {
		Transaction tx =  null;
		try(Session session = HibernateUtil.getSessionFactory().openSession() ) {
			tx = session.beginTransaction();
			
			for (int i = 1; i <= 105; i++) {
				Employee employee = new Employee();
				employee.setEmployeeName("employee name_"+i);
				employee.setUsername("username_"+i);
				employee.setPassword("password_"+i);
				employee.setAccessLevel(1);
				
				session.save(employee);
			}
			
			tx.commit();
			
		} catch (Exception e) {
			if(tx != null && tx.isActive())
				tx.rollback();
			throw e;
		}
	}
}