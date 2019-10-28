package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.AdminService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(value="VetClinic Management System - Admin API",
        description = "Management operations of Admins in the IADI 2019 Pet Clinic")

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
class AdminController(val admins: AdminService) {

    @ApiOperation(value="Get a pet by his ID", response = PetDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully got Pet"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/pets/{id}")
    fun getPet(@PathVariable id:Long) : PetDTO =
            handle4xx { PetDTO(admins.getPetById(id)) }

    @ApiOperation(value="Get a client by his ID", response = ClientDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully got Client"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/clients/{id}")
    fun getClient(@PathVariable id:Long) : ClientDTO =
            handle4xx { ClientDTO(admins.getClientById(id)) }

    @ApiOperation(value="Get an user by his ID", response = UserDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully got user"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id:Long) : UserDTO =
        handle4xx { UserDTO(admins.getUserById(id)) }

    @ApiOperation(value="Get an admin by his ID", response = AdminDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully got admin"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/{id}")
    fun getAdmin(@PathVariable id:Long) : AdminDTO =
            handle4xx { AdminDTO(admins.getAdminById(id)) }

    @ApiOperation(value="Create a new admin", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created admin"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("")
    fun createAdmin(@RequestBody adminDTO:AdminDTO) {
        handle4xx { admins.createAdmin(adminDTO) }
    }

    @ApiOperation(value="Delete an admin", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully frozen admin"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @DeleteMapping("/{id}")
    fun deleteAdmin(@PathVariable id:Long) {
        handle4xx { admins.deleteAdmin(id) }
    }

    @ApiOperation(value="Create a new Veterinarian", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created veterinarian"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("/vets")
    fun createVet(@RequestBody vet:VeterinarianDTO) {
        handle4xx { admins.createVet(vet) }
    }


    @ApiOperation(value="Delete a vet by making him Frozen", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully froze a veterinarian"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("/vets/{id}")
    fun deleteVet(@PathVariable id:Long){
        handle4xx { admins.deleteVet(id) }
    }

    @ApiOperation(value="Get a Veterinarian by his id", response = VeterinarianDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully froze a veterinarian"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/vets/{id}")
    fun getVet(@PathVariable id:Long) : VeterinarianDTO =
            handle4xx { VeterinarianDTO(admins.getVetbyId(id)) }

    @ApiOperation(value="Add a shift to a Veterinarian's schedule", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully created schedule"),
        ApiResponse(code = 401, message = "You are not logged in as admin"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @PostMapping("/vets/shifts/{id}")
    fun setSchedule(@PathVariable id: Long, @RequestBody shifts:List<ShiftsDTO>){
        admins.setSchedule(id, shifts)
    }

    @ApiOperation(value = "Check a Vet's appointments", response = List::class)
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


    @ApiOperation(value = "Update an admin", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully updated an admin"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The admin you tried to update was not found")
    ])
    @PutMapping("/{id}")
    fun updateInfo(@PathVariable id:Long, @RequestBody admin:AdminDTO){
        admins.update(id, admin)
    }
}