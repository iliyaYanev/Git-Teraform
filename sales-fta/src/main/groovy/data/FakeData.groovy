package data

import com.github.javafaker.Faker

class FakeData {
    static faker = new Faker()

    static fullName() {
        return faker.name().fullName()
    }

    static firstName() {
        return faker.name().firstName()
    }

    static lastName() {
        return faker.name().lastName()
    }

    static amount() {
        return faker.number().numberBetween(1, 100)
    }
}
