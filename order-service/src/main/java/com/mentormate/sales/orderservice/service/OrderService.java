package com.mentormate.sales.orderservice.service;

import com.mentormate.sales.orderservice.common.OrderDetails;
import com.mentormate.sales.orderservice.domain.Order;
import com.mentormate.sales.orderservice.repository.OrderRepository;
import com.mentormate.sales.orderservice.saga.createorder.CreateOrderSagaData;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final SagaManager<CreateOrderSagaData> createOrderSagaManager;

	@Transactional
	public Order createOrder(OrderDetails orderDetails){
		CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);
		createOrderSagaManager.create(data);
		return orderRepository.findById(data.getOrderId()).get();
	}
}
