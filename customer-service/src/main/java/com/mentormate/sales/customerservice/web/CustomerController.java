package com.mentormate.sales.customerservice.web;

import java.util.List;

import javax.websocket.server.PathParam;

import com.mentormate.sales.customerservice.domain.Customer;
import com.mentormate.sales.customerservice.service.CustomerService;
import com.mentormate.sales.customerservice.web.dto.CreateCustomerRequest;
import com.mentormate.sales.customerservice.web.dto.CreateCustomerResponse;
import com.mentormate.sales.customerservice.web.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/customers")
	public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
		Customer customer = customerService.createCustomer(createCustomerRequest.getName(), createCustomerRequest.getCreditLimit());
		return new CreateCustomerResponse(customer.getId());
	}

	@GetMapping("/customers")
	public List<CustomerResponse> allCustomers(){
		return customerService.getAll();
	}

	@GetMapping("/customers/{id}")
	public CustomerResponse customerById(@PathVariable("id") Long id){
		return customerService.getById(id);
	}
}

