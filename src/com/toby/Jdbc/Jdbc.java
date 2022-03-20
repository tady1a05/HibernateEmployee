package com.toby.Jdbc;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.toby.Model.Employee;
import com.toby.Utils.DateUtils;



public class Jdbc {

	public static void main(String[] args) throws ParseException {
		SessionFactory sessionFactory = new Configuration()
										.configure("hibernate.cfg.xml")
										.addAnnotatedClass(Employee.class)
										.buildSessionFactory();
		
		//Create
		String dateOfBirth1 = "25/09/1999";
		Employee employee1 = new Employee("Toby","Cheung",DateUtils.parseDate(dateOfBirth1),"Quantr");
		//Create
		String dateOfBirth2 = "25/08/1999";
		Employee employee2 = new Employee("Tony","Wong",DateUtils.parseDate(dateOfBirth2),"ICO Limited");
		
		try {
			saveEmployee(sessionFactory , employee1);
			
			saveEmployee(sessionFactory , employee2);

			queryEmployee(sessionFactory , "Where e.firstName = 'Toby'");
			
			updateEmployee(sessionFactory , "set e.firstName = 'Peter'");
			
			deleteEmployee(sessionFactory , "where e.lastName = 'Wong'");
		}finally {
			sessionFactory.close();
		}
		
	}
	
	public static void saveEmployee(SessionFactory sessionFactory,Employee employee) {			
			Session session = sessionFactory.getCurrentSession();
			
			session.beginTransaction();
			
			if(employee != null) {
				session.save(employee);
			}
			
			session.getTransaction().commit();
	}
	
	public static void queryEmployee(SessionFactory sessionFactory,String query) {			
			Session session = sessionFactory.getCurrentSession();
			
			session.beginTransaction();
			
			List<Employee> Employees = session.createQuery("from Employee e " + query).getResultList();
			
			if(Employees != null) {
				for(Employee employee : Employees) {
					System.out.println(employee.toString());
				}
			}
			
			session.getTransaction().commit();
	}
	
	public static void updateEmployee(SessionFactory sessionFactory,String query) {
			
			Session session = sessionFactory.getCurrentSession();
			
			session.beginTransaction();
			
			int result = session.createQuery("update from Employee e " + query).executeUpdate();
			
			session.getTransaction().commit();
			
	}
	
	public static void deleteEmployee(SessionFactory sessionFactory,String query) {
	
			Session session = sessionFactory.getCurrentSession();
			
			session.beginTransaction();
			
			int result = session.createQuery("delete from Employee e " + query).executeUpdate();
			
			session.getTransaction().commit();
			
		}

}
