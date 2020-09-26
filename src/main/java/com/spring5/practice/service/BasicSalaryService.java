package com.spring5.practice.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring5.practice.config.HibernateConfig;
import com.spring5.practice.model.BasicSalary;


@Service
public class BasicSalaryService {
	private final HibernateConfig hibernateConfig;

	//private static List<Grade> grades = new ArrayList<Grade>();

	@Autowired
	public BasicSalaryService(HibernateConfig hibernateConfig) {
		this.hibernateConfig = hibernateConfig;
	}
	@Transactional
	public void add(BasicSalary bs) {
		all_delete();
		var session = hibernateConfig.getSession();
		var transaction = session.beginTransaction();
		
		session.save(bs);
		transaction.commit();
	}
	public List<BasicSalary> getAll() {

		// **************************** HQL Start ******************************//
		 var session = hibernateConfig.getSession(); 
		 session.beginTransaction(); 
		 var query = session .getEntityManagerFactory() .createEntityManager().createQuery("SELECT c from com.spring5.practice.model.BasicSalary c ",
				 BasicSalary.class);
		 session.close();
		 return query.getResultList();
		}
	public void all_delete() {
		var session = hibernateConfig.getSession();
		var transection = session.beginTransaction();		
		if (!transection.isActive()) {
			transection = session.beginTransaction();
		}
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();	
		CriteriaDelete<BasicSalary> sc = criteriaBuilder.createCriteriaDelete(BasicSalary.class);
		Root<BasicSalary> root = sc.from(BasicSalary.class);
		//sc.where(criteriaBuilder.equal(root.get("id"), gradeId));
			
		var query = session.createQuery(sc);
		try {			
			query.executeUpdate();
			transection.commit();
			 
		}catch(HibernateException e) {
			
			if(transection!= null ) {
				transection.rollback();
			}
			e.printStackTrace();
		}
		
	}
}
