package com.infotech.client;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.infotech.entities.Employee;
import com.infotech.util.HibernateUtil;

public class PaginationClientTest {

	public static void main(String[] args) {
		//paginationUsingCriteriaQuery();
		//paginationUsingHQL();
		completePaginationExample();
	}

	private static void paginationUsingCriteriaQuery() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			int pageSize = 10;
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
			Root<Employee> root = criteriaQuery.from(Employee.class);
			
			CriteriaQuery<Employee> selectQuery = criteriaQuery.select(root);
			
			TypedQuery<Employee> typedQuery = session.createQuery(selectQuery);
			typedQuery.setFirstResult(0);
			typedQuery.setMaxResults(pageSize);
			List<Employee> employeeList = typedQuery.getResultList();
			for (Employee employee : employeeList) {
				System.out.println(employee);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void paginationUsingHQL() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query query = session.createQuery("From Employee");
			int pageSize = 10;
			query.setFirstResult(0);
			query.setMaxResults(pageSize);
			List<Employee> employeeList = query.getResultList();
			for (Employee employee : employeeList) {
				System.out.println(employee);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void completePaginationExample() {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			int pageNumber = 1;
			int pageSize = 10;
			
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
			
			countQuery.select(criteriaBuilder.count(countQuery.from(Employee.class)));
			Long count = session.createQuery(countQuery).getSingleResult();

			CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
			Root<Employee> root = criteriaQuery.from(Employee.class);
			CriteriaQuery<Employee> selectQuery = criteriaQuery.select(root);

			TypedQuery<Employee> typedQuery = session.createQuery(selectQuery);
			while (pageNumber < count.intValue()) {
				System.out.println("page No: " +(pageNumber/pageSize));
				typedQuery.setFirstResult(pageNumber - 1);
				typedQuery.setMaxResults(pageSize);

				for (Employee employee : typedQuery.getResultList()) {
					System.out.println(employee);
				}
				pageNumber += pageSize;
				
				System.out.println("-------------------------------------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
