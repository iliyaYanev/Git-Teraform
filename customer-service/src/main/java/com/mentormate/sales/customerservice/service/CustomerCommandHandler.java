package com.mentormate.sales.customerservice.service;

import java.util.Optional;

import com.mentormate.sales.common.command.ReserveCreditCommand;
import com.mentormate.sales.common.replies.CustomerCreditReservationFailed;
import com.mentormate.sales.common.replies.CustomerCreditReserved;
import com.mentormate.sales.customerservice.domain.Customer;
import com.mentormate.sales.customerservice.domain.CustomerCreditLimitExceededException;
import com.mentormate.sales.customerservice.repository.CustomerRepository;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@RequiredArgsConstructor
@Service
public class CustomerCommandHandler {
	private final CustomerRepository customerRepository;

	public CommandHandlers commandHandlerDefinitions() {
		return SagaCommandHandlersBuilder
				.fromChannel("customerService")
				.onMessage(ReserveCreditCommand.class, this::reserveCredit)
				.build();
	}

	public Message reserveCredit(CommandMessage<ReserveCreditCommand> cm) {
		ReserveCreditCommand cmd = cm.getCommand();
		long customerId = cmd.getCustomerId();
		Optional<Customer> customer = customerRepository.findById(customerId);
		try {
			customer
					.orElseThrow(() -> new IllegalArgumentException(String.format("customer with id %s is not found", customerId)))
					.reserveCredit(cmd.getOrderId(), cmd.getOrderTotal());
			return withSuccess(new CustomerCreditReserved());
		} catch (CustomerCreditLimitExceededException e) {
			return withFailure(new CustomerCreditReservationFailed());
		}
	}

}
