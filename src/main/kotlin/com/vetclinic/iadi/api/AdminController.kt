package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.AdminService
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
class AdminController(val admins: AdminService) {

    @GetMapping("/pets/{id}")
    fun getPet(@PathVariable id:Long) : PetDTO =
            handle4xx { PetDTO(admins.getPetById(id)) }

    @GetMapping("/clients/{id}")
    fun getClient(@PathVariable id:Long) : ClientDTO =
            handle4xx { ClientDTO(admins.getClientById(id)) }

    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id:Long) : UserDTO =
        handle4xx { UserDTO(admins.getUserById(id)) }

    @GetMapping("/{id}")
    fun getAdmin(@PathVariable id:Long) : AdminDTO =
            handle4xx { AdminDTO(admins.getAdminById(id)) }

    @ApiOperation(value="Create a new admin")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully created admin"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("")
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
    @DeleteMapping("/{id}")
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
    @PostMapping("/vets")
    fun createVet(@RequestBody vet:VeterinarianDTO) {
        handle4xx { admins.createVet(vet) }
    }

    @PostMapping("/vets/{id}")
    fun deleteVet(@PathVariable id:Long){
        handle4xx { admins.deleteVet(id) }
    }

    @GetMapping("/vets/{id}")
    fun getVet(@PathVariable id:Long) : VeterinarianDTO =
            handle4xx { VeterinarianDTO(admins.getVetbyId(id)) }

    @ApiOperation(value="Add a shift to a Veterinarian's schedule")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created schedule"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("/vets/shifts/{id}")
    fun setSchedule(@PathVariable id: Long, @RequestBody shifts:List<ShiftsDTO>){
        admins.addShift(id, shifts)
    }

    @ApiOperation(value = "Check a Vet's appointments")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved vet's schedule"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/vets/appointments/{vetId}")
    fun checkAppointments(@PathVariable vetId: Long){
        admins.getVetAppointments(vetId)
    }
}