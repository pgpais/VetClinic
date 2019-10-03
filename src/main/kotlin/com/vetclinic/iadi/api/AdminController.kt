package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.AdminService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value="VetClinic Management System - Admin API",
        description = "Management operations of Admins in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/admin")
class AdminController(val service: AdminService) {

    @ApiOperation(value="Get the details of a single pet")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully created admin"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/create")
    fun createAdmin(adminDTO:AdminDTO) {
        service.createAdmin(adminDTO);
    }

}