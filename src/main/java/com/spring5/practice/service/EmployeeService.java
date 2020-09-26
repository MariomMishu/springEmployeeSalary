package com.spring5.practice.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring5.practice.config.HibernateConfig;
import com.spring5.practice.exceptions.ResourceNotFoundException;
import com.spring5.practice.model.Employee;

@Service
public class EmployeeService {
	@Autowired
	private GradeService gradeService;
	private HibernateConfig hibernateConfig;

	public EmployeeService(HibernateConfig hibernateConfig) {
		this.hibernateConfig = hibernateConfig;
	}

	public void add(Employee emp) {
//
	//	var grade = gradeService.getByCode(emp.getGrade().getGrade());
	//	emp.setGrade(grade);

		var session = hibernateConfig.getSession();
		var transaction = session.getTransaction();
		if (!transaction.isActive()) {
			transaction = session.beginTransaction();
		}
		try {
			session.save(emp);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public List<Employee> getAll() {
		
		// **************************** HQL Start ******************************//
		 var session = hibernateConfig.getSession(); 
		 session.beginTransaction(); 
		 var query = session .getEntityManagerFactory() .createEntityManager().createQuery("SELECT c from com.spring5.practice.model.Employee c ",Employee.class);
		 return query.getResultList();
		// **************************** HQL End ******************************//
		/*
		 * var session = hibernateConfig.getSession(); var transaction =
		 * session.getTransaction(); if (!transaction.isActive()) { transaction =
		 * session.beginTransaction(); }
		 * 
		 * // Start Criteria Query CriteriaBuilder cb = session.getCriteriaBuilder();
		 * CriteriaQuery<Employee> sc = cb.createQuery(Employee.class); Root<Employee>
		 * root = sc.from(Employee.class); sc.select(root); var query =
		 * session.getEntityManagerFactory().createEntityManager().createQuery(sc); //
		 * end Criteria Query
		 * 
		 * var employee_list = query.getResultList(); return employee_list;
		 */
	}

	public Employee getById(long empId) {

		var session = hibernateConfig.getSession();
		var transaction = session.getTransaction();
		if (!transaction.isActive()) {
			transaction = session.beginTransaction();
		}
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Employee> sc = cb.createQuery(Employee.class);
		Root<Employee> root = sc.from(Employee.class);
		sc.where(cb.equal(root.get("id"), empId));
		// sc.select(root);
		var query = session.getEntityManagerFactory().createEntityManager().createQuery(sc);
		var emp_list = query.getResultList();

		return Optional.ofNullable(emp_list.get(0))
				.orElseThrow(() -> new ResourceNotFoundException("Employee Not Found With Thid Id"));
	}

	public void edit(Employee emp) {
		//var grade = gradeService.getByCode(emp.getGrade().getGrade());
		//emp.setGrade(grade);

		var session = hibernateConfig.getSession();
		var transaction = session.getTransaction();
		if (!transaction.isActive()) {
			transaction = session.beginTransaction();
		}
		try {
			session.update(emp);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void delete(long empId) {
		var session = hibernateConfig.getSession();
		var transection = session.beginTransaction();
		if (!transection.isActive()) {
			transection = session.beginTransaction();
		}
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaDelete<Employee> sc = criteriaBuilder.createCriteriaDelete(Employee.class);
		Root<Employee> root = sc.from(Employee.class);
		sc.where(criteriaBuilder.equal(root.get("id"), empId));
		var query = session.createQuery(sc);
		try {
			query.executeUpdate();
		} catch (HibernateException e) {
			if (transection != null) {
				transection.rollback();
			}
			e.printStackTrace();
		}

	}

}
