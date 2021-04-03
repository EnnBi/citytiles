package com.akash.repository.custom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.akash.entity.AppUser;
import com.akash.entity.LabourInfo;
import com.akash.entity.Manufacture;
import com.akash.entity.ManufactureSearch;
import com.akash.entity.dto.ManufactureDTO;
import com.akash.repository.custom.ManufactureCustomizedRepository;
import com.akash.util.Constants;

public class ManufactureCustomizedRepositoryImpl implements ManufactureCustomizedRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<ManufactureDTO> searchPaginated(ManufactureSearch manufactureSearch,int from) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ManufactureDTO> cq = cb.createQuery(ManufactureDTO.class);
		Root<Manufacture> root = cq.from(Manufacture.class);

		List<Predicate> predicates = getPredicates(cb, root, manufactureSearch);
		cq.select(cb.construct(ManufactureDTO.class, root.get("id"), root.get("product").get("name"),
				root.get("size").get("name"),root.get("date"),
				root.get("totalQuantity"), root.get("totalAmount")))
				.where(predicates.toArray(new Predicate[] {}));
		
		cq.orderBy(cb.desc(root.get("date")));
		cq.distinct(true);
		return em.createQuery(cq).setFirstResult(from).setMaxResults(Constants.ROWS).getResultList();
	}

	public List<Predicate> getPredicates(CriteriaBuilder cb, Root<Manufacture> root, ManufactureSearch manufactureSearch) {
		List<Predicate> predicates = new ArrayList<>();

		
		if (manufactureSearch.getProductId() != null)
			predicates.add(cb.equal(root.get("product").get("id"), manufactureSearch.getProductId()));
		if (manufactureSearch.getSizeId() != null)
			predicates.add(cb.equal(root.get("size").get("id"), manufactureSearch.getSizeId()));
		if (manufactureSearch.getLabourId() != null){
			Join<Manufacture,LabourInfo> labourInfoJoin = root.join("labourInfo");
			Join<LabourInfo,AppUser> labourJoin = labourInfoJoin.join("labours");
			predicates.add(cb.equal(labourJoin.get("id"), manufactureSearch.getLabourId()));
		}

		predicates.add(cb.between(root.get("date"), manufactureSearch.getStartDate(), manufactureSearch.getEndDate()));

		return predicates;
	}

	
	@Override
	public long count(ManufactureSearch manufactureSearch) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Manufacture> root = cq.from(Manufacture.class);

		List<Predicate> predicates = getPredicates(cb, root, manufactureSearch);
		cq.select(cb.count(root))
				.where(predicates.toArray(new Predicate[] {}));
		
		cq.orderBy(cb.desc(root.get("date")));
		cq.distinct(true);
		try {
			return em.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0;
		}
	}
	
	public boolean isNotNullOrNotEmpty(String string) {
		if (string != null)
			if (!string.isEmpty())
				return true;

		return false;
	}

}
