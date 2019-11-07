package data.api

import common.ApiConsumer
import data.ApiPaths
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.Method

@Slf4j
class OrderRequests {
    static port = '8080'
    static int retryTime = 10 * 1000 //10s

    static getAllOrders() {
        return ApiConsumer.executeRequest(ApiPaths.Order.orders, port, Method.GET)
    }

    static getOrderById(id) {
        def path = ApiPaths.Order.orderById.replace('{id}', id.toString())
        return ApiConsumer.executeRequest(path, port, Method.GET)
    }

    static createOrder(customerId, amount) {
        def body = "{ \"customerId\": ${customerId}, \"orderTotal\": { \"amount\": ${amount} } }"

        return ApiConsumer.executeRequest(ApiPaths.Order.orders, port, Method.POST, body)
    }

    static getOrderProcessingStatus(orderId) {
        def orderState
        while (retryTime >= 0) {
            orderState = getOrderById(orderId).body.state
            if (orderState != "PENDING") {
                log.info("Order: ${orderId} status is '${orderState}'!")
                break
            }
            retryTime -= 500
            log.info("Order: ${orderId} status is '${orderState}'! Waiting for 500 more milliseconds!")
            Thread.sleep(500)
        }

        log.info("Order: ${orderId} status is '${orderState}' after waiting for 10 seconds!")
        return orderState
    }
}
