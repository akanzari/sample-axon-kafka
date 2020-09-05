package com.axonkafka.interceptors;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.axonkafka.domain.Catalog;
import com.axonkafka.exception.UnicityException;

public class NameUniquenessCheck {

	private static final String NAME = "name";

	private EntityManager entityManager;

	public NameUniquenessCheck(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected void check(String name) {
		FullTextQuery fullTextQuery = getFullTextEntityManager().createFullTextQuery(getQuery(name), Catalog.class);
		int catalogs = fullTextQuery.getResultSize();
		if (catalogs > 0) {
			throw new UnicityException(Catalog.class, NAME, name);
		}
	}

	private Query getQuery(String name) {
		return getQueryBuilderCatalog().keyword().onField(NAME).matching(name).createQuery();
	}

	private FullTextEntityManager getFullTextEntityManager() {
		return Search.getFullTextEntityManager(entityManager);
	}

	private QueryBuilder getQueryBuilderCatalog() {
		FullTextEntityManager fullTextEntityManager = getFullTextEntityManager();
		return fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Catalog.class).get();
	}

}