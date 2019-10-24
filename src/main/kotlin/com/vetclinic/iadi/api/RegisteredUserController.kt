package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.RegisteredUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "VetClinic Management System - Access API",
        description = "Management access operations in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/{username}")
class RegisteredUserController (val regUserService: RegisteredUserService){


    @ApiOperation(value = "Get the details of your pet by Id", response = PetDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved pet details"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    ])
    
    @GetMapping("/pets/{id}")
    fun getPetById(@PathVariable id:Long, @PathVariable username:String){}


    @ApiOperation(value = "Get the details of your pet by Id", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved pet details"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    ])
    @GetMapping("/pets")
    fun getAllPets(@PathVariable username:String){}




}