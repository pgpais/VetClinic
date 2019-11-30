package com.vetclinic.iadi.api


import ch.qos.logback.core.net.server.Client
import com.vetclinic.iadi.services.ClientService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(value="VetClinic Management System - Client API",
        description = "Management operations of Client in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/client")
class ClientController(val clients:ClientService){

    @ApiOperation(value="Get client by username", response = ClientDTO::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieve a client"),
            ApiResponse(code = 401, message = "You are not authorized to use this resource"),
            ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            ApiResponse(code = 404, message = "User does not exist")
    )
    @PreAuthorize("hasRole('ROLE_VET') or hasRole('ROLE_CLIENT')")
    @GetMapping("/byUsername/{username}")
    fun getOneClientByUsername(@PathVariable username:String) : ClientDTO =
            handle4xx { clients.getClientByUsername(username).let { ClientDTO(it.id, it.name, it.username, it.pass, it.photo, it.email, it.phone, it.address) } }

    @ApiOperation(value="Get client by Id", response = ClientDTO::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieve a client"),
            ApiResponse(code = 401, message = "You are not authorized to use this resource"),
            ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            ApiResponse(code = 404, message = "User does not exist")
    )
    @PreAuthorize("hasRole('ROLE_VET') or hasRole('ROLE_CLIENT')")
    @GetMapping("/{id}")
    fun getOneClient(@PathVariable id:Long) : ClientDTO =
            handle4xx { clients.getClientById(id).let{ ClientDTO(it.id, it.name, it.username, it.pass, it.photo, it.email, it.phone, it.address) } }

    @ApiOperation(value = "Update a client", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully updated a client"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The client you tried to update was not found")
    ])
    @PreAuthorize("hasRole('ROLE_CLIENT') and @securityService.isClient(principal, #id)")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody client:ClientDTO){
        clients.update(id, client)
    }

    fun checkUser(){

    }
    @ApiOperation(value = "Gets a user's Pets", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully got pets"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "Some information could not be found")])
    @PreAuthorize("hasRole('ROLE_CLIENT') and @securityService.isClient(principal, #userId)")
    @GetMapping("/{userId}/pets")
    fun getPets(@PathVariable userId: Long): List<PetDTO> =
            handle4xx { clients.getPets(userId).map{PetDTO(it)}
            }

    //TODO: remove from here
    /*
    @ApiOperation(value="Get appointments of this user", response = List::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieve client's appointments"),
            ApiResponse(code = 401, message = "You are not authorized to use this resource"),
            ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            ApiResponse(code = 404, message = "User does not exist")
    )
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/apts/{userId}")
    fun getAppointments(@PathVariable userId:Long): List<AppointmentDTO> =
            handle4xx { clients.getAppointments(userId).map{AppointmentDTO(it)} }


    @ApiOperation(value = "Book appointment for this user", response = Unit::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully booked appointment"),
            ApiResponse(code = 401, message = "You are not authorized to use this resource"),
            ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            ApiResponse(code = 404, message = "Some information could not be found")
    )
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping("/apts")
    fun bookAppointment(@RequestBody apt:AppointmentDTO){
        clients.bookAppointment(apt)
    }

    @ApiOperation(value = "Add user's Pets", response = Unit::class)
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully added pets"),
            ApiResponse(code = 401, message = "You are not authorized to use this resource"),
            ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            ApiResponse(code = 404, message = "Some information could not be found")
    )
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping("/pets/{userId}")
    fun createPet(@PathVariable userId: Long, pet:PetDTO) =
            handle4xx { clients.createPet(userId, pet) }


    */
}
