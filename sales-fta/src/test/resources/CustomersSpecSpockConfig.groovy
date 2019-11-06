import common.annotations.CustomersTests
import common.annotations.OrdersTests

runner {
    include CustomersTests
    exclude OrdersTests
}
