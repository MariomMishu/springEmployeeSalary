package com.spring5.practice.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import com.spring5.practice.config.HibernateConfig;
import com.spring5.practice.exceptions.ResourceNotFoundException;
import com.spring5.practice.model.Account;

@Service
public class AccountService {
	private HibernateConfig hibernateConfig;

	public AccountService(HibernateConfig hibernateConfig) {
		this.hibernateConfig = hibernateConfig;
	}

	public void add(Account ac) {

		var session = hibernateConfig.getSession();
		var transaction = session.getTransaction();
		if (!transaction.isActive()) {
			transaction = session.beginTransaction();
		}
		try {
			session.save(ac);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	public List<Account> getAll() {
		var session = hibernateConfig.getSession();
		var transaction = session.getTransaction();
		if (!transaction.isActive()) {
			transaction = session.beginTransaction();
		}

		// Start Criteria Query
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Account> sc = cb.createQuery(Account.class);
		Root<Account> root = sc.from(Account.class);
		sc.select(root);
		var query = session.getEntityManagerFactory().createEntityManager().createQuery(sc);
		// end Criteria Query

		var ac_list = query.getResultList();
		return ac_list;
	}

	public Account getById(long acId) {

		var session = hibernateConfig.getSession();
		var transaction = session.getTransaction();
		if (!transaction.isActive()) {
			transaction = session.beginTransaction();
		}
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Account> sc = cb.createQuery(Account.class);
		Root<Account> root = sc.from(Account.class);
		sc.where(cb.equal(root.get("id"), acId));
		// sc.select(root);
		var query = session.getEntityManagerFactory().createEntityManager().createQuery(sc);
		var ac_list = query.getResultList();

		return Optional.ofNullable(ac_list.get(0))
				.orElseThrow(() -> new ResourceNotFoundException("Account Not Found With Thid Id"));
	}

	public void edit(Account ac) {
		var session = hibernateConfig.getSession();
		var transaction = session.getTransaction();
		if (!transaction.isActive()) {
			transaction = session.beginTransaction();
		}
		try {
			session.update(ac);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void delete(long acId) {
		var session = hibernateConfig.getSession();
		var transection = session.beginTransaction();
		if (!transection.isActive()) {
			transection = session.beginTransaction();
		}
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaDelete<Account> sc = criteriaBuilder.createCriteriaDelete(Account.class);
		Root<Account> root = sc.from(Account.class);
		sc.where(criteriaBuilder.equal(root.get("id"), acId));
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
