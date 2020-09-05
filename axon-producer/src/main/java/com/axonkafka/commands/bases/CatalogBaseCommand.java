package com.axonkafka.commands.bases;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CatalogBaseCommand<T> {

	@TargetAggregateIdentifier
	public final T catalogId;

	public CatalogBaseCommand(T catalogId) {
		this.catalogId = catalogId;
	}

}