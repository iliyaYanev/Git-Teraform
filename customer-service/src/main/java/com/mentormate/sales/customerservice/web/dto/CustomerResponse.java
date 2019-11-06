package com.mentormate.sales.customerservice.web.dto;

import javax.persistence.Embedded;

import com.mentormate.sales.common.value.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

	private Long id;
	private String name;
	private Money creditLimit;
}
