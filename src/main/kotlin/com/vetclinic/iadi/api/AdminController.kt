package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.AdminService
import com.vetclinic.iadi.services.VetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*
import java.util.*

@Api(value="VetClinic Management System - Admin API",
        description = "Management operations of Admins in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/admin")
class AdminController(val admins: AdminService, val vets: VetService) {

    @ApiOperation(value="Create a new admin")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully created admin"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("/createAdmin")
    fun createAdmin(@RequestBody adminDTO:AdminDTO) {
        handle4xx { admins.createAdmin(adminDTO) }
    }

    @ApiOperation(value="Delete an admin")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully created admin"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @DeleteMapping("/deleteAdmin/{id}")
    fun deleteAdmin(@PathVariable id:Long) {
        handle4xx { admins.deleteAdmin(id) }
    }

    @ApiOperation(value="Create a new Veterinarian")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully created veterinarian"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("/createVet")
    fun createVet(@RequestBody vetDTO:VeterinarianDTO) {
        handle4xx { admins.createVet(vetDTO) }
    }

    @ApiOperation(value="Set a schedule for a veterinarian")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created schedule"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("/setSchedule/{vetId}")
    fun setSchedule(@PathVariable vetId: Long, @RequestBody adminId: Long, @RequestBody shifts:List<Pair<Date, Date>>){
        vets.setSchedule(vetId, adminId, shifts)
    }

    @ApiOperation(value = "Check a Vet's appointments")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved vet's schedule"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/appointments/{vetId}")
    fun checkAppointments(@PathVariable vetId: Long){
        vets.getAppointments(vetId)
    }
}