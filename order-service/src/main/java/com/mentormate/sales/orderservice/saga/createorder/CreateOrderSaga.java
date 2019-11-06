package com.mentormate.sales.orderservice.saga.createorder;

import com.mentormate.sales.common.command.ReserveCreditCommand;
import com.mentormate.sales.common.value.Money;
import com.mentormate.sales.orderservice.domain.Order;
import com.mentormate.sales.orderservice.repository.OrderRepository;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@RequiredArgsConstructor
@Service
public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {

	private final OrderRepository orderRepository;

	private SagaDefinition<CreateOrderSagaData> sagaDefinition =
		step()
				.invokeLocal(this::create)
				.withCompensation(this::reject)
		.step()
				.invokeParticipant(this::reserveCredit)
		.step()
				.invokeLocal(this::approve)
		.build();

	@Override
	public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
		return this.sagaDefinition;
	}

	private void approve(CreateOrderSagaData data) {
		orderRepository.findById(data.getOrderId())
				.ifPresent(Order::noteCreditReserved);
	}

	private CommandWithDestination reserveCredit(CreateOrderSagaData data) {
		long orderId = data.getOrderId();
		Long customerId = data.getOrderDetails().getCustomerId();
		Money orderTotal = data.getOrderDetails().getOrderTotal();
		return send(new ReserveCreditCommand(orderId, orderTotal, customerId))
				.to("customerService")
				.build();
	}


	private void create(CreateOrderSagaData data) {
		ResultWithEvents<Order> createOrder = Order.createOrder(data.getOrderDetails());
		Order order = createOrder.result;
		orderRepository.save(order);
		data.setOrderId(order.getId());
	}

	private void reject(CreateOrderSagaData data) {
		orderRepository.findById(data.getOrderId())
				.ifPresent(Order::noteCreditReservationFailed);
	}
}
