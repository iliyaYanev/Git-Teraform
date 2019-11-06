package com.mentormate.sales.orderservice.domain;

import java.util.Collections;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.mentormate.sales.common.entity.BaseEntity;
import com.mentormate.sales.orderservice.common.OrderDetails;
import io.eventuate.tram.events.publisher.ResultWithEvents;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private OrderState state;

	@Embedded
	private OrderDetails orderDetails;

	public Order(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
		this.state = OrderState.PENDING;
	}

	public void noteCreditReserved() {
		this.state = OrderState.APPROVED;
	}

	public  void noteCreditReservationFailed() {
		this.state = OrderState.REJECTED;
	}

	public static ResultWithEvents<Order> createOrder(OrderDetails orderDetails) {
		return new ResultWithEvents<Order>(new Order(orderDetails), Collections.emptyList());
	}

}
