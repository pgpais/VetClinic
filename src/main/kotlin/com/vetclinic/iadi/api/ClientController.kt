package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.services.AppointmentService
import com.vetclinic.iadi.services.ClientService
import com.vetclinic.iadi.services.PetService
import com.vetclinic.iadi.services.VetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Api(value="VetClinic Management System - Client API",
        description = "Management operations of Client in the IADI 2019 Pet Clinic")

@RestController
@PreAuthorize("hasRole('ROLE_CLIENT')")
@RequestMapping("/client")
class ClientController(val client:ClientService, val apts:AppointmentService, val pets:PetService, val vets:VetService){ //TODO: so many services up here....


    @GetMapping("/{id}")
    fun getOneClient(@PathVariable id:Long) : ClientDTO =
            handle4xx { client.getClientById(id).let{ ClientDTO(it.id, it.username, it.pass) } }

    @ApiOperation(value="Get appointments of this user")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieve client's appointments"),
            ApiResponse(code = 404, message = "User does not exist")
    )
    @GetMapping("/apts/{userId}")
    fun getAppointments(@PathVariable userId:Long): List<AppointmentDTO> =
            handle4xx { client.getAppointments(userId).map{AppointmentDTO(it)} }


    @ApiOperation(value = "Book appointment for this user")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully booked appointment")
    )
    @PostMapping("/apts/{userId}")
    fun bookAppointment(@PathVariable userId:Long, @RequestBody apt:AppointmentDTO){
        //TODO: the client needs to choose a pet (which is chosen on appointment?)
        apts.newAppointment(AppointmentDAO(apt, pets.getPetByID(apt.petId), client.getClientById(apt.clientId), vets.getVetbyId(apt.vetId))) //TODO: check this, so many arguments
    }

    @GetMapping("/pets/{userId}")
    fun getPets(@PathVariable userId: Long): List<PetDTO> =
            handle4xx { client.getPets(userId).map{PetDTO(it)} }


    @PostMapping("/pets/{userId}")
    fun addPet(@PathVariable userId: Long, @RequestBody pet:PetDTO){
        // TODO:
        pets.addNew(PetDAO(pet, client.getClientById(userId)))
    }

    @DeleteMapping("/pets/{userId}/{petId}")
    fun deletePet(@PathVariable userId: Long, @PathVariable petId: Long){
        // TODO: remove pet without removing it
        // remove the ownerId?
    }
}