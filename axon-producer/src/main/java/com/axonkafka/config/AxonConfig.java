package com.axonkafka.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.axonframework.springboot.util.jpa.ContainerManagedEntityManagerProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.axonkafka.interceptors.CatalogDispatchInterceptor;
import com.axonkafka.interceptors.CheckFoundCatalog;
import com.axonkafka.interceptors.NameUniquenessCheck;

@Configuration
@AutoConfigureAfter(value = { AxonAutoConfiguration.class })
public class AxonConfig {

	@Bean
	public SnapshotTriggerDefinition catalogSnapshotTrigger(Snapshotter snapshotter) {
		return new EventCountSnapshotTriggerDefinition(snapshotter, 3);
	}

	@Bean
	@ConditionalOnMissingBean
	public EntityManagerProvider entityManagerProvider() {
		return new ContainerManagedEntityManagerProvider();
	}

	@Bean
	public NameUniquenessCheck nameUniquenessCheck() {
		return new NameUniquenessCheck(entityManagerProvider().getEntityManager());
	}

	@Bean
	public CheckFoundCatalog checkFoundCatalog() {
		return new CheckFoundCatalog(entityManagerProvider().getEntityManager());
	}

	@Bean
	public CommandBus configureCommandBus() {
		CommandBus commandBus = SimpleCommandBus.builder().build();
		commandBus.registerDispatchInterceptor(
				new CatalogDispatchInterceptor(nameUniquenessCheck(), checkFoundCatalog()));
		return commandBus;
	}

}