package com.mentormate.sales.customerservice.configuration;

import com.mentormate.sales.customerservice.service.CustomerCommandHandler;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(SagaParticipantConfiguration.class)
public class CustomerConfiguration {

	@Bean
	public CommandDispatcher consumerCommandDispatcher(CustomerCommandHandler target,
			SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {

		return sagaCommandDispatcherFactory.make("customerCommandDispatcher", target.commandHandlerDefinitions());
	}

}
