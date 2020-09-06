package com.axonkafka.aggregate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.core.annotation.Order;

import com.axonkafka.commands.AttachProductCommand;
import com.axonkafka.commands.CreateCatalogCommand;
import com.axonkafka.commands.DeleteCatalogCommand;
import com.axonkafka.commands.UpdateCatalogCommand;
import com.axonkafka.domain.Product;
import com.axonkafka.events.CatalogCreatedEvent;
import com.axonkafka.events.CatalogDeletedEvent;
import com.axonkafka.events.CatalogUpdatedEvent;
import com.axonkafka.events.ProductAttachedEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Aggregate(snapshotTriggerDefinition = "catalogSnapshotTrigger")
@NoArgsConstructor // constructor needed for reconstructing the aggregate
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CatalogAggregate {

	@AggregateIdentifier
	String catalogId;

	String name;

	String description;

	Set<Product> products = new HashSet<>();

	@CommandHandler
	public CatalogAggregate(CreateCatalogCommand cmd) {
		AggregateLifecycle.apply(new CatalogCreatedEvent(cmd.catalogId, cmd.name, cmd.description));
	}

	@EventSourcingHandler
	@Order(1)
	protected void on(CatalogCreatedEvent evt) {
		this.catalogId = evt.catalogId;
		this.name = evt.name;
		this.description = evt.description;
	}

	@CommandHandler
	public void handle(UpdateCatalogCommand cmd) {
		AggregateLifecycle.apply(new CatalogUpdatedEvent(cmd.catalogId, cmd.description));
	}

	@EventSourcingHandler
	@Order(1)
	protected void on(CatalogUpdatedEvent evt) {
		this.catalogId = evt.catalogId;
	}

	@CommandHandler
	public void handle(AttachProductCommand cmd) {
		AggregateLifecycle
				.apply(new ProductAttachedEvent(cmd.catalogId, cmd.name, cmd.description, cmd.price, cmd.quantity));
	}

	@EventSourcingHandler
	@Order(1)
	protected void on(ProductAttachedEvent evt) {
		this.catalogId = evt.catalogId;
		this.updateProducts(evt);
	}

	private void updateProducts(ProductAttachedEvent evt) {
		if (CollectionUtils.isNotEmpty(this.products)) {
			Optional<Product> productOptional = this.products.stream()
					.filter((Product product) -> product.getName().equals(evt.name)).findFirst();
			if (productOptional.isPresent()) {
				this.products.remove(productOptional.get());
			}
		}
		this.products.add(Product.builder().name(evt.name).description(evt.description).price(evt.price)
				.quantity(evt.quantity).build());
	}

	@CommandHandler
	public void handle(DeleteCatalogCommand cmd) {
		AggregateLifecycle.apply(new CatalogDeletedEvent(cmd.catalogId));
	}

	@EventSourcingHandler
	@Order(1)
	protected void on(CatalogDeletedEvent evt) {
		AggregateLifecycle.markDeleted();
	}

}