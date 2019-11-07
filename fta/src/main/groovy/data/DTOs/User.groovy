package data.DTOs

import data.FakeData
import groovy.transform.builder.Builder

@Builder
class User {

    String firstName
    String lastName
    String email

    User() {
        this.firstName = FakeData.firstName()
        this.lastName = FakeData.lastName()
        this.email = "${firstName}.${lastName}@email.com"
    }

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    @Override
    String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
