package com.vetclinic.iadi.api


import com.vetclinic.iadi.services.ClientService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*

@Api(value="VetClinic Management System - Client API",
        description = "Management operations of Client in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/client")
class ClientController(val clients:ClientService){

    @ApiOperation(value="Get client by Id")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieve a client"),
            ApiResponse(code = 404, message = "User does not exist")
    )
    @GetMapping("/{id}")
    fun getOneClient(@PathVariable id:Long) : ClientDTO =
            handle4xx { clients.getClientById(id).let{ ClientDTO(it.id, it.name, it.username, it.pass, it.photo, it.email, it.phone, it.address) } }

    @ApiOperation(value="Get appointments of this user")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieve client's appointments"),
            ApiResponse(code = 404, message = "User does not exist")
    )
    @GetMapping("/apts/{userId}")
    fun getAppointments(@PathVariable userId:Long): List<AppointmentDTO> =
            handle4xx { clients.getAppointments(userId).map{AppointmentDTO(it)} }


    @ApiOperation(value = "Book appointment for this user")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully booked appointment")
    )
    @PostMapping("/apts")
    fun bookAppointment(@RequestBody apt:AppointmentDTO){
        clients.bookAppointment(apt)
    }

    @ApiOperation(value = "Gets a user's Pets")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully got pets")
    )
    @GetMapping("/pets/{userId}")
    fun getPets(@PathVariable userId: Long): List<PetDTO> =
            handle4xx { clients.getPets(userId).map{PetDTO(it)}
            }

    @ApiOperation(value = "Add user's Pets")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully added pets")
    )
    @PostMapping("/pets/{userId}")
    fun createPet(@PathVariable userId: Long, pet:PetDTO) =
            handle4xx { clients.createPet(userId, pet) }


    @ApiOperation(value = "Delete a pet")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully 'deleted' pet")
    )
    @DeleteMapping("/pets/{userId}/{petId}")
    fun deletePet(@PathVariable userId: Long, @PathVariable petId: Long){
        clients.deleteClientsPet(userId, petId)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody client:ClientDTO){
        clients.update(id, client)
    }
}