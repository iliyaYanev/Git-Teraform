package com.mentormate.sales.customerservice.service;

import java.util.ArrayList;
import java.util.List;

import com.mentormate.sales.common.value.Money;
import com.mentormate.sales.customerservice.domain.Customer;
import com.mentormate.sales.customerservice.repository.CustomerRepository;
import com.mentormate.sales.customerservice.web.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {
	private final CustomerRepository customerRepository;

	public Customer createCustomer(String name, Money creditLimit) {
		Customer customer  = new Customer(name, creditLimit);
		return customerRepository.save(customer);
	}

	public List<CustomerResponse> getAll(){
		var customers = new ArrayList<CustomerResponse>();
		customerRepository.findAll().forEach(customer ->{
			customers.add(new CustomerResponse(customer.getId(),customer.getName(), customer.getCreditLimit()));
		});
		return customers;
	}

	public CustomerResponse getById(Long id) {
		var customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotExist());
		return new CustomerResponse(customer.getId(),customer.getName(), customer.getCreditLimit());
	}
}
