package com.mentormate.sales.customerservice.domain;


import java.util.Collections;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mentormate.sales.common.entity.BaseEntity;
import com.mentormate.sales.common.value.Money;
import lombok.Data;

@Data
@Entity
@Table(name="Customer")
public class Customer extends BaseEntity {

	private String name;

	@Embedded
	private Money creditLimit;

	@ElementCollection
	private Map<Long, Money> creditReservations;

	Money availableCredit() {
		return creditLimit.subtract(creditReservations.values().stream().reduce(Money.ZERO, Money::add));
	}

	public Customer() {
	}

	public Customer(String name, Money creditLimit) {
		this.name = name;
		this.creditLimit = creditLimit;
		this.creditReservations = Collections.emptyMap();
	}

	public void reserveCredit(Long orderId, Money orderTotal) {
		if (availableCredit().isGreaterThanOrEqual(orderTotal)) {
			creditReservations.put(orderId, orderTotal);
		} else
			throw new CustomerCreditLimitExceededException();
	}
}
