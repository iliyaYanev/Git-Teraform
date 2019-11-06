import common.annotations.CustomersTests
import common.annotations.OrdersTests

runner {
    include OrdersTests
    exclude CustomersTests
}

