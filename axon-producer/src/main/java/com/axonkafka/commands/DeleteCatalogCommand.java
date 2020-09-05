package com.axonkafka.commands;

import com.axonkafka.commands.bases.CatalogBaseCommand;

public class DeleteCatalogCommand extends CatalogBaseCommand<String> {

	public DeleteCatalogCommand(String externalId) {
		super(externalId);
	}

}