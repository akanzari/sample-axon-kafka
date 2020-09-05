package com.axonkafka.query.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axonkafka.domain.Catalog;

public interface CatalogRepository extends JpaRepository<Catalog, String> {

}