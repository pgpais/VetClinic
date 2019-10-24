package com.vetclinic.iadi.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*


@Api(value = "VetClinic Management System - Access API",
        description = "Management access operations in the IADI 2019 Pet Clinic")


@RestController
@RequestMapping("")
class UnRegUserController { //TODO: add service (call it something related with accounts?)

    @ApiOperation(value = "Login with given user") // TODO: should probably return a token
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully logged in"), //201 for successful token creation?
        ApiResponse(code = 404, message = "Could not find provided user"),
        ApiResponse(code = 403, message = "Could not login with provided information")

    ])
    @PostMapping("/login")
    fun login(@RequestBody username:String, @RequestBody password:String) =
            {
                // TODO: execute login, maybe encrypt password here
            }

    @ApiOperation(value = "Register a new user")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created a new user"),
        ApiResponse(code = 403, message = "You cannot access this resource (maybe already logged in?)")
    ])
    @PostMapping("/register")
    fun register(@RequestBody user:ClientDTO) = {
            // TODO: create new ClientDAO and save it in database?
    }

    @ApiOperation(value = "Get the list of Employees", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved the list of Employees"),
        ApiResponse(code = 404, message = "There are no Employees registered")
    ])
    @GetMapping("/listVets")
    fun listVets():List<VeterinarianDTO>  {
        return emptyList()
    }
}