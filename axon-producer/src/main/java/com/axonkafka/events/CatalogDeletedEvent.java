package com.axonkafka.events;

import com.axonkafka.events.bases.CatalogBaseEvent;

public class CatalogDeletedEvent extends CatalogBaseEvent<String> {

	private static final long serialVersionUID = 1L;

	public CatalogDeletedEvent(String catalogId) {
		super(catalogId);
	}

}