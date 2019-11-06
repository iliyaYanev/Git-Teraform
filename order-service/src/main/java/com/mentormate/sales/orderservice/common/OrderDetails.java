package com.mentormate.sales.orderservice.common;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.mentormate.sales.common.value.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class OrderDetails {

	private Long customerId;

	@Embedded
	private Money orderTotal;
}
