package com.axonkafka.commands;

import com.axonkafka.commands.bases.CatalogBaseCommand;

public class UpdateCatalogCommand extends CatalogBaseCommand<String> {

	public final String description;

	public UpdateCatalogCommand(String externalId, String description) {
		super(externalId);
		this.description = description;
	}

}