package com.axonkafka.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axonkafka.domain.Catalog;
import com.axonkafka.dto.AttachProductRequest;
import com.axonkafka.dto.CreateCatalogRequest;
import com.axonkafka.dto.UpdateCatalogRequest;
import com.axonkafka.mapper.CatalogCommandMapper;
import com.axonkafka.services.CatalogService;
import com.axonkafka.util.HeaderUtil;
import com.axonkafka.util.ResponseUtil;

@RestController
@RequestMapping(value = "catalogs")
public class CatalogController {

	private final CatalogService service;

	private final CatalogCommandMapper mapper;

	@Value("${spring.application.name}")
	private String applicationName;

	public CatalogController(CatalogService service, CatalogCommandMapper mapper) {
		this.service = service;
		this.mapper = mapper;
	}

	@PostMapping
	public ResponseEntity<String> createCatalogAction(@RequestBody @Valid CreateCatalogRequest createCatalogRequest)
			throws URISyntaxException {
		String externalId = service.createCatalog(mapper.createCatalogRequestToCatalogDomain(createCatalogRequest));
		return ResponseEntity.created(new URI("/catalogs/" + externalId)).headers(HeaderUtil
				.createEntityCreationAlert(applicationName, true, CreateCatalogRequest.class.getName(), externalId))
				.body(externalId);

	}

	@PatchMapping
	public ResponseEntity<Void> updateCatalogAction(@RequestBody @Valid UpdateCatalogRequest updateCatalogRequest) {
		service.updateCatalog(updateCatalogRequest.getCatalogId(),
				mapper.updateCatalogRequestToCatalogDomain(updateCatalogRequest));
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true,
				CreateCatalogRequest.class.getName(), updateCatalogRequest.getCatalogId())).body(null);
	}

	@PutMapping
	public ResponseEntity<Void> attachProductAction(@RequestBody @Valid AttachProductRequest attachProductRequest) {
		service.attachCatalog(attachProductRequest.getCatalogId(),
				mapper.attachProductRequestToProductDomain(attachProductRequest));
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true,
				AttachProductRequest.class.getName(), attachProductRequest.getCatalogId())).body(null);
	}

	@GetMapping
	public List<Catalog> getAllCatalogs() {
		return service.getAllCatalogs();
	}

	@GetMapping("/{catalogId}")
	public ResponseEntity<Catalog> getCatalog(@PathVariable String catalogId) {
		Optional<Catalog> catalog = service.getCatalog(catalogId);
		return ResponseUtil.wrapOrNotFound(catalog);
	}

	@DeleteMapping("/{catalogId}")
	public ResponseEntity<Void> deleteBankAccount(@PathVariable String catalogId) {
		service.deleteCatalog(catalogId);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, null, catalogId)).build();
	}

	@GetMapping("/events/{catalogId}")
	public List<Object> getEventById(@PathVariable String catalogId) {
		return service.getEventById(catalogId);
	}

}