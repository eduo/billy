/**
 * Copyright (C) 2013 Premium Minds.
 *
 * This file is part of billy platypus (PT Pack).
 *
 * billy platypus (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy platypus (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy platypus (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.persistence.dao.jpa;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.premiumminds.billy.core.persistence.dao.jpa.DAOTaxImpl;
import com.premiumminds.billy.portugal.persistence.dao.DAOPTTax;
import com.premiumminds.billy.portugal.persistence.entities.PTRegionContextEntity;
import com.premiumminds.billy.portugal.persistence.entities.PTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTTaxEntity;
import com.premiumminds.billy.portugal.persistence.entities.jpa.JPAPTTaxEntity_;

public class DAOPTTaxImpl extends DAOTaxImpl implements DAOPTTax {

	@Inject
	public DAOPTTaxImpl(Provider<EntityManager> emProvider) {
		super(emProvider);
	}

	@Override
	public PTTaxEntity getEntityInstance() {
		return new JPAPTTaxEntity();
	}

	@Override
	protected Class<JPAPTTaxEntity> getEntityClass() {
		return JPAPTTaxEntity.class;
	}

	public List<JPAPTTaxEntity> getTaxes(PTRegionContextEntity context,
			Date validFrom, Date validTo, String type) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<JPAPTTaxEntity> cq = cb.createQuery(JPAPTTaxEntity.class);

		Root<JPAPTTaxEntity> tax = cq.from(JPAPTTaxEntity.class);

		cq.select(tax);
		cq.where(cb.and(cb.equal(tax.get(JPAPTTaxEntity_.description), type),
				cb.equal(tax.get(JPAPTTaxEntity_.validFrom), validFrom),
				cb.equal(tax.get(JPAPTTaxEntity_.validTo), validTo),
				cb.equal(tax.get(JPAPTTaxEntity_.active), true),
				cb.equal(tax.get(JPAPTTaxEntity_.context), context)));
		TypedQuery<JPAPTTaxEntity> q = em.createQuery(cq);
		List<JPAPTTaxEntity> list = q.getResultList();
		return list;
	}
}
