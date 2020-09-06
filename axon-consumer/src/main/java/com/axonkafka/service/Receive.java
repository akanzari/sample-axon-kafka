package com.axonkafka.service;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.axonkafka.events.CatalogCreatedEvent;

@Component
@ProcessingGroup("catalogs")
public class Receive {

	private static final Logger logger = LoggerFactory.getLogger(Receive.class);

	@EventHandler
	public void on(CatalogCreatedEvent event) {
		logger.info("Catalog Id {}", event.catalogId);
	}

}