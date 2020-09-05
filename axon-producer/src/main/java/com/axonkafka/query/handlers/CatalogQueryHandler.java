package com.axonkafka.query.handlers;

import java.util.Optional;
import java.util.Set;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.axonkafka.domain.Catalog;
import com.axonkafka.domain.Product;
import com.axonkafka.events.CatalogCreatedEvent;
import com.axonkafka.events.CatalogUpdatedEvent;
import com.axonkafka.events.ProductAttachedEvent;
import com.axonkafka.query.repositories.CatalogRepository;

@Component
public class CatalogQueryHandler {

	@Autowired
	private CatalogRepository repository;

	private void persistCatalog(Catalog catalog) {
		this.repository.save(catalog);
	}

	private Catalog getCatalog(String catalogId) {
		Catalog catalogEntity = null;
		Optional<Catalog> catalogEntityOptional = repository.findById(catalogId);
		if (catalogEntityOptional.isPresent()) {
			catalogEntity = catalogEntityOptional.get();
		}
		return catalogEntity;
	}

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRED)
	@Order(2)
	public void on(CatalogCreatedEvent event) {
		persistCatalog(
				Catalog.builder().catalogId(event.catalogId).name(event.name).description(event.description).build());
	}

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRED)
	@Order(2)
	public void on(CatalogUpdatedEvent event) {
		Catalog catalog = getCatalog(event.catalogId);
		if (catalog != null) {
			catalog.setDescription(event.description);
			persistCatalog(catalog);
		}
	}

	@EventHandler
	@Transactional(propagation = Propagation.REQUIRED)
	@Order(2)
	public void on(ProductAttachedEvent event) {
		Catalog catalog = getCatalog(event.catalogId);
		if (catalog != null) {
			Set<Product> products = catalog.getProducts();
			products.add(Product.builder().name(event.name).description(event.description).price(event.price)
					.quantity(event.quantity).build());
			catalog.setProducts(products);
			persistCatalog(catalog);
		}
	}

}