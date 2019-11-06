package com.mentormate.sales.orderservice.web.dto;


import com.mentormate.sales.common.value.Money;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
  private Money orderTotal;
  private Long customerId;
}
