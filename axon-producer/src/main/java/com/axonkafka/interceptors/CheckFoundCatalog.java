package com.axonkafka.interceptors;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.axonkafka.domain.Catalog;
import com.axonkafka.exception.EntityNotFoundException;

public class CheckFoundCatalog {

	private static final String CATALOGID = "catalogId";

	private EntityManager entityManager;

	public CheckFoundCatalog(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected void check(String catalogId) {
		FullTextQuery fullTextQuery = getFullTextEntityManager().createFullTextQuery(getQuery(catalogId),
				Catalog.class);
		int catalogs = fullTextQuery.getResultSize();
		if (catalogs == 0) {
			throw new EntityNotFoundException(Catalog.class, CATALOGID, catalogId);
		}
	}

	private Query getQuery(String catalogId) {
		return getQueryBuilderCatalog().keyword().onField(CATALOGID).matching(catalogId).createQuery();
	}

	private FullTextEntityManager getFullTextEntityManager() {
		return Search.getFullTextEntityManager(entityManager);
	}

	private QueryBuilder getQueryBuilderCatalog() {
		FullTextEntityManager fullTextEntityManager = getFullTextEntityManager();
		return fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Catalog.class).get();
	}

}