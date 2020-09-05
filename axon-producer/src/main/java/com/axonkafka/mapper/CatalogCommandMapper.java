package com.axonkafka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.axonkafka.domain.Catalog;
import com.axonkafka.domain.Product;
import com.axonkafka.dto.AttachProductRequest;
import com.axonkafka.dto.CreateCatalogRequest;
import com.axonkafka.dto.UpdateCatalogRequest;

@Mapper
public interface CatalogCommandMapper {

	@Mapping(target = "catalogId", ignore = true)
	@Mapping(target = "products", ignore = true)
	Catalog createCatalogRequestToCatalogDomain(CreateCatalogRequest createCatalogRequest);

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "products", ignore = true)
	Catalog updateCatalogRequestToCatalogDomain(UpdateCatalogRequest updateCatalogRequest);

	Product attachProductRequestToProductDomain(AttachProductRequest attachProductRequest);

}