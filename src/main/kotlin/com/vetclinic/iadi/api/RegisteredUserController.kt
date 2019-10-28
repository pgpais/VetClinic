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
@RequestMapping("/home/{username}")
class RegisteredUserController (val regUserService: RegisteredUserService){

    @ApiOperation(value = "Update user info")
    @PutMapping("")
    fun updateInfo(@PathVariable username: String, @RequestBody newUser:UserDTO){
        regUserService.updateInfo(username, newUser)
    }
}