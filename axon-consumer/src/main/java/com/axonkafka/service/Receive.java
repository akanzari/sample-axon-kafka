package com.axonkafka.service;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import com.axonkafka.events.CatalogCreatedEvent;

@Component
@ProcessingGroup("catalogs")
public class Receive {

	@EventHandler
	public void on(CatalogCreatedEvent event) {
		System.out.println("Catalog Id : " + event.catalogId);
	}

}