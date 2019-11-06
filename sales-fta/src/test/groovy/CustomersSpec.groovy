import common.annotations.CustomersTests
import data.DTOs.Customer
import data.FakeData
import data.api.CustomerRequests
import groovy.util.logging.Slf4j
import spock.lang.Issue
import spock.lang.See
import spock.lang.Specification
import spock.lang.Title
import spock.lang.Unroll

@CustomersTests
@Slf4j
@Title("Functional tests for customer service")
class CustomersSpec extends Specification {
    Customer customer

    def setup() {
        customer = new Customer()
    }

    @Issue("https://JiraStoryLink-1")
    @See("https://LinkWithMoreInfoForTheStory-1")
    def createCustomer() {
        when: "I create a new customer"
            def createCustomerResponse = CustomerRequests.createCustomer(customer)
        then: "Returned status status should be 200"
            assert createCustomerResponse.statusCode == 200
        and: "The id of the customer should be returned"
            assert createCustomerResponse.body.customerId ==~ /\d+/
            def customerId = createCustomerResponse.body.customerId
            log.info("Customer: ${customer} has CustomerId: ${customerId}")
        when: "I get the data for the customer"
            def getCustomerDataResponse = CustomerRequests.getCustomerById(customerId)
        then: "Returned data should be correct"
            verifyAll {
                getCustomerDataResponse.statusCode == 200
                getCustomerDataResponse.body.id == customerId
                getCustomerDataResponse.body.name == customer.name
                getCustomerDataResponse.body.creditLimit.amount == customer.amount
            }
    }

    @Issue("https://JiraStoryLink-2")
    @See("https://LinkWithMoreInfoForTheStory-2")
    @Unroll("Create Customer with name: #customer.name and amount: #customer.amount - V1")
    def createCustomerDataDriven() {
        when: "I create a new customer"
            def createCustomerResponse = CustomerRequests.createCustomer(customer)
        then: "Returned status status should be 200"
            assert createCustomerResponse.statusCode == 200
        and: "The id of the customer should be returned"
            assert createCustomerResponse.body.customerId ==~ /\d+/
            def customerId = createCustomerResponse.body.customerId
            log.info("Customer: ${customer} has CustomerId: ${customerId}")
        when: "I get the data for the customer"
            def getCustomerDataResponse = CustomerRequests.getCustomerById(customerId)
        then: "Returned data should be correct"
            verifyAll {
                getCustomerDataResponse.statusCode == 200
                getCustomerDataResponse.body.id == customerId
                getCustomerDataResponse.body.name == customer.name
                getCustomerDataResponse.body.creditLimit.amount == customer.amount
            }
        where: "Customer data"
        customer << [new Customer(),
                     new Customer(name: FakeData.fullName(), amount: FakeData.amount()),
                     Customer.builder().name(FakeData.fullName()).amount(FakeData.amount()).build()
                    ]
    }

    @Issue("https://JiraStoryLink-2")
    @See("https://LinkWithMoreInfoForTheStory-2")
    @Unroll()
    def "Create Customer with name: #customer.name and amount: #customer.amount - V2"() {
        when: "I create a new customer"
            def createCustomerResponse = CustomerRequests.createCustomer(customer)
        then: "Returned status status should be 200"
            assert createCustomerResponse.statusCode == 200
        and: "The id of the customer should be returned"
            assert createCustomerResponse.body.customerId ==~ /\d+/
            def customerId = createCustomerResponse.body.customerId
            log.info("Customer: ${customer} has CustomerId: ${customerId}")
        when: "I get the data for the customer"
            def getCustomerDataResponse = CustomerRequests.getCustomerById(customerId)
        then: "Returned data should be correct"
            verifyAll {
                getCustomerDataResponse.statusCode == 200
                getCustomerDataResponse.body.id == customerId
                getCustomerDataResponse.body.name == customer.name
                getCustomerDataResponse.body.creditLimit.amount == customer.amount
            }
        where: "Customer data"
        customer << [new Customer(),
                     new Customer(name: FakeData.fullName(), amount: FakeData.amount()),
                     Customer.builder().name(FakeData.fullName()).amount(FakeData.amount()).build()
        ]
    }
}
