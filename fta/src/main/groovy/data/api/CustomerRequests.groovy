package data.api

import common.ApiConsumer
import data.ApiPaths
import data.DTOs.Customer
import groovyx.net.http.ContentType
import groovyx.net.http.Method

class CustomerRequests {
    static port = '8081'

    static getAllCustomers() {
        return ApiConsumer.executeRequest(ApiPaths.Customer.customers, port, Method.GET)
    }

    static getCustomerById(id) {
        def path = ApiPaths.Customer.customerById.replace('{id}', id.toString())
        return ApiConsumer.executeRequest(path, port, Method.GET)
    }

    static createCustomer(Customer customer) {
        def body = "{ \"creditLimit\": { \"amount\": ${customer.amount} }, \"name\": \"${customer.name}\" }"

        return ApiConsumer.executeRequest(ApiPaths.Customer.customers, port, Method.POST, body)
    }
}
