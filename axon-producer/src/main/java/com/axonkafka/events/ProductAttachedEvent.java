package com.axonkafka.events;

import com.axonkafka.events.bases.CatalogBaseEvent;

public class ProductAttachedEvent extends CatalogBaseEvent<String> {

	private static final long serialVersionUID = 1L;

	public final String name;

	public final String description;

	public final double price;

	public final double quantity;

	public ProductAttachedEvent(String catalogId, String name, String description, double price, double quantity) {
		super(catalogId);
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

}