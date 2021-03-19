package com.akash.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

import com.akash.entity.RawMaterial;
import com.akash.entity.RawMaterialSearch;
import com.akash.repository.custom.RawMaterialCustomizedRepository;

public class RawMaterialCustomizedRepositoryImpl  implements RawMaterialCustomizedRepository{
	
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RawMaterial> searchRawMaterialPaginated(RawMaterialSearch rawMaterialSearch,int from) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<RawMaterial> cq = cb.createQuery(RawMaterial.class);
		Root<RawMaterial> root = cq.from(RawMaterial.class);
		List<Predicate> predicates=getPredicates(root, cb, rawMaterialSearch);
		
		cq.select(root).where(predicates.toArray(new Predicate[] {}));
		cq.distinct(true);
		cq.orderBy(cb.desc(root.get("date")),cb.desc(root.get("id")));

		Query query=em.createQuery(cq);
		
		return query.setFirstResult(from).setMaxResults(10).getResultList();

}
	public List<Predicate> getPredicates(Root<RawMaterial> root,CriteriaBuilder cb,RawMaterialSearch rawMaterialSearch){
		List<Predicate> predicates=new ArrayList<>();
		
		if(isNotNullOrNotEmpty(rawMaterialSearch.getAppUserId()))
			predicates.add(cb.equal(root.get("dealer").get("id"), rawMaterialSearch.getAppUserId()));
		
		if(isNotNullOrNotEmpty(rawMaterialSearch.getMaterialTypeId()))
			predicates.add(cb.equal(root.get("material").get("id"),rawMaterialSearch.getMaterialTypeId()));
		
		if(rawMaterialSearch.getStartDate()!=null)
			predicates.add(cb.greaterThanOrEqualTo(root.get("date"),rawMaterialSearch.getStartDate()));
		
		if(rawMaterialSearch.getStartDate()!=null)
			predicates.add(cb.lessThanOrEqualTo(root.get("date"), rawMaterialSearch.getEndDate()));
		
		return predicates;
	}
	
	@Override
	public long searchRawMaterialsCount(RawMaterialSearch rawMaterialSearch) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<RawMaterial> root = cq.from(RawMaterial.class);
		
		List<Predicate> predicates=getPredicates(root, cb, rawMaterialSearch);
		
		cq.distinct(true);
		cq.multiselect(cb.count(root)).where(predicates.toArray(new Predicate[] {}));
		
		cq.orderBy(cb.desc(root.get("date")));

		Query query=em.createQuery(cq);
		
		try {
			return (long) query.getSingleResult();
		} catch (Exception e) {
			return 0;
		}
	}
	public boolean isNotNullOrNotEmpty(Long	 lng){
		if(lng!=null)
		return true;
		
		return false;
	}
}
	
	
