package com.axonkafka.events;

import com.axonkafka.events.bases.CatalogBaseEvent;

public class CatalogCreatedEvent extends CatalogBaseEvent<String> {

	private static final long serialVersionUID = 1L;

	public final String name;

	public final String description;

	public CatalogCreatedEvent(String catalogId, String name, String description) {
		super(catalogId);
		this.name = name;
		this.description = description;
	}

}