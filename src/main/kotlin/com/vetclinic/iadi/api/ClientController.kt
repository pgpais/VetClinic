package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.ClientService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value="VetClinic Management System - Client API",
        description = "Management operations of Clients in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/client")
class ClientController(val service:ClientService) {

    @ApiOperation(value="Login with given username and password")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully logged in"),
        ApiResponse(code = 404, message = "Username not found"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("/login")
    fun login(client:ClientDTO)  {
        // Perform Login
        service.login(client);
    }

    @ApiOperation(value="Register a new user")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully registered"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("/register")
    fun register(client:ClientDTO){
        service.register(client);
    }

    @ApiOperation(value="Get a list of employees", response = EmployeeDTO::class, responseContainer = "List")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully got the list"),
        ApiResponse(code = 404, message = "Page was not found")
    ])
    @GetMapping("/employees")
    fun getEmployeeList() = emptyList<EmployeeDTO>()
}