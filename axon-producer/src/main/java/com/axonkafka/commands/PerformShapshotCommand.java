package com.axonkafka.commands;

import com.axonkafka.commands.bases.CatalogBaseCommand;

public class PerformShapshotCommand extends CatalogBaseCommand<String> {

	public PerformShapshotCommand(String externalId) {
		super(externalId);
	}

}