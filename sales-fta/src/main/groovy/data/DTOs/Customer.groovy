package data.DTOs

import data.FakeData
import groovy.transform.builder.Builder

@Builder
class Customer {
    String id
    String name
    int amount

    Customer() {
        this.name = FakeData.fullName()
        this.amount = FakeData.amount()
    }

    String toString() {
        return "name: ${this.name}, amount: ${this.amount}"
    }
}
