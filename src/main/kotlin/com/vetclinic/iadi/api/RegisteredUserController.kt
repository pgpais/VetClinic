package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.RegisteredUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*

@Api(value = "VetClinic Management System - Access API",
        description = "Management access operations in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/home")
class RegisteredUserController (val regUserService: RegisteredUserService){


    @ApiOperation(value = "Update a user's info", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully updated a user's information"),
        ApiResponse(code = 404, message = "User not found"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])

    @PutMapping("/{username}")
    fun updateInfo(@PathVariable username: String, @RequestBody newUser:UserDTO){
        regUserService.updateInfo(username, newUser)
    }
}