package com.axonkafka.services;

import java.util.List;
import java.util.Optional;

import com.axonkafka.domain.Catalog;
import com.axonkafka.domain.Product;

public interface CatalogService {
	
	default String createCatalog(Catalog catalog) { return null; }
	
	default void updateCatalog(String catalogId, Catalog catalog) { }
	
	default void attachCatalog(String catalogId, Product product) { }
	
	default void deleteCatalog(String catalogId) { }
	
	default List<Catalog> getAllCatalogs() { return null; }
	
	default Optional<Catalog> getCatalog(String extenalId) { return null; }
	
	default List<Object> getEventById(String catalogId) { return null; }

}