package com.axonkafka.commands;

import com.axonkafka.commands.bases.CatalogBaseCommand;

public class CreateCatalogCommand extends CatalogBaseCommand<String> {

	public final String name;

	public final String description;

	public CreateCatalogCommand(String externalId, String name, String description) {
		super(externalId);
		this.name = name;
		this.description = description;
	}

}