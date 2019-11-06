package com.mentormate.sales.orderservice.web;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import com.mentormate.sales.orderservice.common.OrderDetails;
import com.mentormate.sales.orderservice.domain.Order;
import com.mentormate.sales.orderservice.repository.OrderRepository;
import com.mentormate.sales.orderservice.service.OrderService;
import com.mentormate.sales.orderservice.web.dto.CreateOrderRequest;
import com.mentormate.sales.orderservice.web.dto.CreateOrderResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

	private final OrderService orderService;
	private final OrderRepository orderRepository;


	@PostMapping("/orders")
	public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
		Order order = orderService.createOrder(new OrderDetails(createOrderRequest.getCustomerId(), createOrderRequest.getOrderTotal()));
		return new CreateOrderResponse(order.getId());
	}

	@GetMapping("/orders")
	public List<Order> getAll() {
		var orders = new ArrayList<Order>();
		orderRepository.findAll().forEach(order -> {
			orders.add(order);
		});
		return orders;
	}

	@GetMapping("/orders/{id}")
	public Order getById(@PathVariable("id") Long id){
		return orderRepository.findById(id).get();
	}
}
