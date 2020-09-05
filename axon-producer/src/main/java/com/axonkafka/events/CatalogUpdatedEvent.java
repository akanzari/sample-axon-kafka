package com.axonkafka.events;

import com.axonkafka.events.bases.CatalogBaseEvent;

public class CatalogUpdatedEvent extends CatalogBaseEvent<String> {

	private static final long serialVersionUID = 1L;

	public final String description;

	public CatalogUpdatedEvent(String externalId, String description) {
		super(externalId);
		this.description = description;
	}

}