package com.axonkafka.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.stereotype.Service;

import com.axonkafka.commands.AttachProductCommand;
import com.axonkafka.commands.CreateCatalogCommand;
import com.axonkafka.commands.DeleteCatalogCommand;
import com.axonkafka.commands.PerformShapshotCommand;
import com.axonkafka.commands.UpdateCatalogCommand;
import com.axonkafka.domain.Catalog;
import com.axonkafka.domain.Product;
import com.axonkafka.dto.AggregateHistoryDTO;
import com.axonkafka.exception.EntityNotFoundException;
import com.axonkafka.query.repositories.CatalogRepository;
import com.axonkafka.services.CatalogService;

@Service
public class CatalogServiceImpl implements CatalogService {

	private final CommandGateway commandGateway;

	private final CatalogRepository catalogRepository;

	private final EventStore eventStore;

	public CatalogServiceImpl(CommandGateway commandGateway, CatalogRepository catalogRepository,
			EventStore eventStore) {
		this.commandGateway = commandGateway;
		this.catalogRepository = catalogRepository;
		this.eventStore = eventStore;
	}

	@Override
	public String createCatalog(Catalog catalog) {
		return commandGateway.sendAndWait(
				new CreateCatalogCommand(UUID.randomUUID().toString(), catalog.getName(), catalog.getDescription()));
	}

	@Override
	public void updateCatalog(String catalogId, Catalog catalog) {
		commandGateway.sendAndWait(new UpdateCatalogCommand(catalog.getCatalogId(), catalog.getDescription()));
	}

	@Override
	public void attachCatalog(String catalogId, Product product) {
		commandGateway.sendAndWait(new AttachProductCommand(catalogId, product.getName(), product.getDescription(),
				product.getPrice(), product.getQuantity()));
	}

	@Override
	public void deleteCatalog(String catalogId) {
		getCatalog(catalogId);
		commandGateway.sendAndWait(new DeleteCatalogCommand(catalogId));
	}

	@Override
	public List<Catalog> getAllCatalogs() {
		return catalogRepository.findAll();
	}

	@Override
	public Optional<Catalog> getCatalog(String catalogId) {
		Optional<Catalog> catalog = catalogRepository.findById(catalogId);
		if (!catalog.isPresent()) {
			throw new EntityNotFoundException(Catalog.class, "extenalId", catalogId);
		}
		return catalog;
	}

	@Override
	public void createSnapshot(String catalogId) {
		commandGateway.sendAndWait(new PerformShapshotCommand(catalogId));
	}

	@Override
	public List<Object> getEventById(String catalogId) {
		return eventStore.readEvents(catalogId).asStream().map(this::domainEventToAggregateHistory)
				.collect(Collectors.toList());
	}

	private AggregateHistoryDTO domainEventToAggregateHistory(DomainEventMessage<?> event) {
		return AggregateHistoryDTO.builder().name(event.getPayloadType().getSimpleName()).event(event.getPayload())
				.sequenceNumber(event.getSequenceNumber()).timestamp(event.getTimestamp()).build();
	}

}