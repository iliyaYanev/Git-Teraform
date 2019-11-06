package com.mentormate.sales.orderservice.saga.createorder;

import com.mentormate.sales.orderservice.common.OrderDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderSagaData {
	private Long orderId;
	private OrderDetails orderDetails;

	public CreateOrderSagaData(OrderDetails orderDetails){
		this.orderDetails = orderDetails;
	}
}
