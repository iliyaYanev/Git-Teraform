import common.annotations.OrdersTests
import data.DTOs.Customer
import data.api.CustomerRequests
import data.api.OrderRequests
import groovy.util.logging.Slf4j
import spock.lang.Issue
import spock.lang.See
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@OrdersTests
@Slf4j
@Title("Functional tests for customer service")
class OrdersSpec extends Specification {
    Customer customer
    int customerId

    @Issue("OrdersJiraStoryLink-1")
    @See("LinkWithMoreInfoForTheStory-1")
    def createOrder() {
        given: "I create a new customer"
            customer = new Customer()
            def createCustomerResponse = CustomerRequests.createCustomer(customer)
            customerId = createCustomerResponse.body.customerId
        when: "I create a new order"
            def orderAmount = 5
            def createOrderDataResponse = OrderRequests.createOrder(customerId, orderAmount)
        then: "Returned status status should be 200"
            createOrderDataResponse.statusCode == 200
        and: "The id of the order should be returned"
            assert createOrderDataResponse.body.orderId ==~ /\d+/
            def orderId = createOrderDataResponse.body.orderId
        when: "I get the order details"
            def getOrderDetails = OrderRequests.getOrderById(orderId)
        then: "Order details should be correct"
            verifyAll {
                getOrderDetails.statusCode == 200
                getOrderDetails.body.id ==~ /\d+/
                getOrderDetails.body.state == "PENDING"
                getOrderDetails.body.orderDetails.customerId == customerId
                getOrderDetails.body.orderDetails.orderTotal.amount == orderAmount
            }
    }

    @Issue("OrdersJiraStoryLink-1")
    @See("LinkWithMoreInfoForTheStory-1")
    @Unroll("Create order with amount #orderAmount for user: #customerName with total amount: #customerAmount")
    def createOrderAndCheckCustomerAmount() {
        given: "I create a new customer"
            customer = new Customer(name: customerName, amount: customerAmount)
            def createCustomerResponse = CustomerRequests.createCustomer(customer)
            customerId = createCustomerResponse.body.customerId
        when: "I create a new order"
            def createOrderDataResponse = OrderRequests.createOrder(customerId, orderAmount)
        then: "Returned status status should be 200"
            createOrderDataResponse.statusCode == 200
        and: "The id of the order should be returned"
            assert createOrderDataResponse.body.orderId ==~ /\d+/
            def orderId = createOrderDataResponse.body.orderId
        and: "The order status should be #expectedOrderStatus"
            OrderRequests.getOrderProcessingStatus(orderId) == expectedOrderStatus
        where: "Customer and order details"
        customerName     | customerAmount | orderAmount || expectedOrderStatus
        "Test User 0001" | 100            | 99          || "APPROVED"
        "Test User 0002" | 50             | 50          || "APPROVED"
        "Test User 0003" | 10             | 11          || "REJECTED"
    }
}
