package com.akash.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.akash.entity.DayBook;
import com.akash.entity.DayBookSearch;
import com.akash.repository.custom.DayBookCustomizedRepository;
import com.akash.util.Constants;

public class DayBookCustomizedRepositoryImpl implements DayBookCustomizedRepository{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<DayBook> searchPaginated(DayBookSearch dayBookSearch, int from) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DayBook> cq = cb.createQuery(DayBook.class);
		Root<DayBook> root = cq.from(DayBook.class);

		List<Predicate> predicates = getPredicates(cb, root, dayBookSearch);
		cq.select(root).where(predicates.toArray(new Predicate[] {}));
		cq.orderBy(cb.desc(root.get("date")));
		cq.distinct(true);
		return em.createQuery(cq).setFirstResult(from).setMaxResults(Constants.ROWS).getResultList();
	}

	private List<Predicate> getPredicates(CriteriaBuilder cb, Root<DayBook> root, DayBookSearch dayBookSearch) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(dayBookSearch.getUser() != null){
			predicates.add(cb.equal(root.get("user").get("id"),dayBookSearch.getUser()));
		}
		
		if(isNotNullAndNotEmpty(dayBookSearch.getTransactionBy())){
			predicates.add(cb.equal(root.get("transactionBy"), dayBookSearch.getTransactionBy()));
		}
		
		if(isNotNullAndNotEmpty(dayBookSearch.getTransactionNumber())){
			predicates.add(cb.equal(root.get("transactionNumber"), dayBookSearch.getTransactionNumber()));
		}
			
		if(isNotNullAndNotEmpty(dayBookSearch.getTransactionType())){
			predicates.add(cb.equal(root.get("transactionType"), dayBookSearch.getTransactionType()));
		}
		
		if(isNotNullAndNotEmpty(dayBookSearch.getAccountNumber())){
			predicates.add(cb.equal(root.get("accountNumber"),dayBookSearch.getAccountNumber()));
		}
		
		if(isNotNullAndNotEmpty(dayBookSearch.getStatus())){
			predicates.add(cb.equal(root.get("status"),dayBookSearch.getStatus()));
		}
		
		predicates.add(cb.between(root.get("date"), dayBookSearch.getStartDate(), dayBookSearch.getEndDate()));

		return predicates;
	}

	@Override
	public long count(DayBookSearch dayBookSearch) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<DayBook> root = cq.from(DayBook.class);

		List<Predicate> predicates = getPredicates(cb, root, dayBookSearch);
		cq.select(cb.count(root)).where(predicates.toArray(new Predicate[] {}));
		cq.orderBy(cb.desc(root.get("date")));
		cq.distinct(true);
		try {
			return em.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public boolean isNotNullAndNotEmpty(String source){
		if(source != null)
			if(!source.isEmpty())
				return true;
		return false;
	}

}
