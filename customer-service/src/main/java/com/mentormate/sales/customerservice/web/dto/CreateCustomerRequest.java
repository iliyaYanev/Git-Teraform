package com.mentormate.sales.customerservice.web.dto;

import com.mentormate.sales.common.value.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {
  private String name;
  private Money creditLimit;
}
