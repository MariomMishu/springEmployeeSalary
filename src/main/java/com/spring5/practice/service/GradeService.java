package com.spring5.practice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring5.practice.config.HibernateConfig;
import com.spring5.practice.exceptions.ResourceAlreadyExistsException;
import com.spring5.practice.exceptions.ResourceNotFoundException;
import com.spring5.practice.model.Grade;

@Service
public class GradeService {
	
	private final HibernateConfig hibernateConfig;

	private static List<Grade> grades = new ArrayList<Grade>();

	@Autowired
	public GradeService(HibernateConfig hibernateConfig) {
		this.hibernateConfig = hibernateConfig;
	}
	@Transactional
	public void add(Grade grade) {
		var session = hibernateConfig.getSession();
		var transaction = session.beginTransaction();
		session.save(grade);
		transaction.commit();
	}

	public void checkInList(Grade g) {
		if (grades.stream().filter(grade -> grade.getGrade().equals(g.getGrade())).findAny()
				.isPresent()) {
			throw new ResourceAlreadyExistsException("Grade already exists in list");
		}
	}

	public Grade getByCode(String grade) {
	
		// **************************** Criteria Query Start
		// **************************//
		CriteriaBuilder cb = hibernateConfig.getCriteriaBuilder();
		CriteriaQuery<Grade> cq = cb.createQuery(Grade.class);
		Root<Grade> root = cq.from(Grade.class);
		cq.where(cb.equal(root.get("grade"), grade));
		var result = hibernateConfig.getSession().getEntityManagerFactory().createEntityManager().createQuery(cq)
				.getResultList();

		// **************************** Criteria Query End **************************//
		return Optional.ofNullable(result.get(0))
				.orElseThrow(() -> new ResourceNotFoundException("Grade not found with this code"));
	}

	public List<Grade> getAll() {

		// **************************** HQL Start ******************************//
//		 var session = hibernateConfig.getSession(); session.beginTransaction(); var
//		 query = session .getEntityManagerFactory() .createEntityManager().createQuery("SELECT c from com.spring5.practice.model.Country c ",
//		 Country.class);
//		 return query.getResultList();
		// **************************** HQL End ******************************//

		// **************************** Criteria Query Start
		// **************************//
		CriteriaBuilder cb = hibernateConfig.getCriteriaBuilder();
		CriteriaQuery<Grade> cq = cb.createQuery(Grade.class);
		Root<Grade> root = cq.from(Grade.class);
		cq.select(root);
		List<Grade> countries = hibernateConfig.getSession().getEntityManagerFactory().createEntityManager()
				.createQuery(cq).getResultList();
		// **************************** Criteria Query End **************************//
		return countries;
	}

	public Grade getById(long gradeId) {
		CriteriaBuilder cb = hibernateConfig.getCriteriaBuilder();
		CriteriaQuery<Grade> cq = cb.createQuery(Grade.class);
		Root<Grade> root = cq.from(Grade.class);
		cq.where(cb.equal(root.get("id"), gradeId));
		var result = hibernateConfig.getSession().getEntityManagerFactory().createEntityManager().createQuery(cq)
				.getResultList();

		// **************************** Criteria Query End **************************//
		return Optional.ofNullable(result.get(0))
				.orElseThrow(() -> new ResourceNotFoundException("Grade not found with this Id"));
	}

	public void edit(Grade grade) {
		var session = hibernateConfig.getSession();
		var trans = session.getTransaction();

		if (!trans.isActive()) {
			trans = session.beginTransaction();
		}

		try {
			session.update(grade);
			trans.commit();

		} catch (HibernateException e) {
			if (trans != null) {
				trans.rollback();
			}
			e.printStackTrace();
		}

	}
	public void all_delete() {
		var session = hibernateConfig.getSession();
		var transection = session.beginTransaction();		
		if (!transection.isActive()) {
			transection = session.beginTransaction();
		}
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();	
		CriteriaDelete<Grade> sc = criteriaBuilder.createCriteriaDelete(Grade.class);
		Root<Grade> root = sc.from(Grade.class);
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
	public void delete(long gradeId) {
			var session = hibernateConfig.getSession();
			var transection = session.beginTransaction();		
			if (!transection.isActive()) {
				transection = session.beginTransaction();
			}
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();	
			CriteriaDelete<Grade> sc = criteriaBuilder.createCriteriaDelete(Grade.class);
			Root<Grade> root = sc.from(Grade.class);
			sc.where(criteriaBuilder.equal(root.get("id"), gradeId));
				
			var query = session.createQuery(sc);
			try {			
				query.executeUpdate();
				 
			}catch(HibernateException e) {
				
				if(transection!= null ) {
					transection.rollback();
				}
				e.printStackTrace();
			}
		
		}
}
