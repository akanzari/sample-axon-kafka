package com.axonkafka.commands;

import com.axonkafka.commands.bases.CatalogBaseCommand;

public class AttachProductCommand extends CatalogBaseCommand<String> {

	public final String name;

	public final String description;

	public final double price;

	public final double quantity;

	public AttachProductCommand(String catalogId, String name, String description, double price, double quantity) {
		super(catalogId);
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

}