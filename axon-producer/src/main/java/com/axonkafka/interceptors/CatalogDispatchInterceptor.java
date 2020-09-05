package com.axonkafka.interceptors;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;

import com.axonkafka.commands.AttachProductCommand;
import com.axonkafka.commands.CreateCatalogCommand;
import com.axonkafka.commands.DeleteCatalogCommand;
import com.axonkafka.commands.UpdateCatalogCommand;

public class CatalogDispatchInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

	private NameUniquenessCheck nameUniquenessCheck;

	private CheckFoundCatalog checkFoundCatalog;

	public CatalogDispatchInterceptor(NameUniquenessCheck nameUniquenessCheck, CheckFoundCatalog checkFoundCatalog) {
		this.nameUniquenessCheck = nameUniquenessCheck;
		this.checkFoundCatalog = checkFoundCatalog;
	}

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {
		return (index, command) -> {
			if (CreateCatalogCommand.class.getName().equals(command.getPayloadType().getName())) {
				CreateCatalogCommand createCatalogCommand = (CreateCatalogCommand) command.getPayload();
				nameUniquenessCheck.check(createCatalogCommand.name);
			}
			
			if (UpdateCatalogCommand.class.getName().equals(command.getPayloadType().getName())) {
				UpdateCatalogCommand updateCatalogCommand = (UpdateCatalogCommand) command.getPayload();
				checkFoundCatalog.check(updateCatalogCommand.catalogId);
			}
			
			if (AttachProductCommand.class.getName().equals(command.getPayloadType().getName())) {
				AttachProductCommand attachProductCommand = (AttachProductCommand) command.getPayload();
				checkFoundCatalog.check(attachProductCommand.catalogId);
			}
			
			if (DeleteCatalogCommand.class.getName().equals(command.getPayloadType().getName())) {
				DeleteCatalogCommand deleteCatalogCommand = (DeleteCatalogCommand) command.getPayload();
				checkFoundCatalog.check(deleteCatalogCommand.catalogId);
			}
			return command;
		};
	}

}