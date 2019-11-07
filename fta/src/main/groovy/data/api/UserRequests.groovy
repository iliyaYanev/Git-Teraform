package data.api

import common.ApiConsumer
import data.ApiPaths
import data.DTOs.User
import groovyx.net.http.Method

class UserRequests {
    static port = '9090'

    static getAllUsers() {
        return ApiConsumer.executeRequest(ApiPaths.User.users, port, Method.GET)
    }

    static getUserById(id) {
        def path = ApiPaths.User.userById.replace('{id}', id.toString())
        return ApiConsumer.executeRequest(path, port, Method.GET)
    }

    static addUser(User user) {
        def body = "\"email\": \"${user.getEmail()}\",\n" +
                "  \"firstName\": \"${user.getFirstName()}\",\n" +
                "  \"lastName\": \"${user.getLastName()}\""

        return ApiConsumer.executeRequest(ApiPaths.User.users, port, Method.POST, body)
    }

    static deleteUser(id) {
        def path = ApiPaths.User.userById.replace('{id}', id.toString())
        return ApiConsumer.executeRequest(path, port, Method.DELETE)
    }
}
