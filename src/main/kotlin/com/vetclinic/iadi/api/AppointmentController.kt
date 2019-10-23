package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.ClientDAO
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.VeterinarianDAO
import com.vetclinic.iadi.services.AppointmentService
import com.vetclinic.iadi.services.ClientService
import com.vetclinic.iadi.services.PetService
import com.vetclinic.iadi.services.VetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*

@Api(value = "VetClinic Management System - Appointment API",
        description = "Management operations of Appointments in the IADI 2019 Pet Clinic")


@RestController
@RequestMapping("/appointments")
class AppointmentController(val apts: AppointmentService, val petService: PetService, val clientService: ClientService, val vetService: VetService) {

    @ApiOperation(value = "View a list of appointments", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("")
    fun getAllAppointments() : List<AppointmentDTO> =
            apts.getAllAppointments().map { AppointmentDTO(it.id, it.date, it.desc, it.status, it.reason, it.pet.id, it.client.id, it.vet.id) }

    @ApiOperation(value = "Get the details of an appointment by Id", response = AppointmentDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved pet details"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    ])
    @GetMapping("/{id}")
    fun getOneAppointment(@PathVariable id:Long) : AppointmentDTO =
            handle4xx { apts.getAppointmentByID(id).let { AppointmentDTO(it.id, it.date, it.desc, it.status, it.reason, it.pet.id, it.client.id, it.vet.id) } }


    @ApiOperation(value = "Add an appointment", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully added an appointment"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @PostMapping("")
    fun newAppointment(@RequestBody apt: AppointmentDTO) =
            handle4xx {
                apts.newAppointment(AppointmentDAO(apt, petService.getPetByID(apt.petId), clientService.getClientById(apt.clientId), vetService.getVetbyId(apt.vetId)))
            }

    @ApiOperation(value = "Delete an appointment", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully deleted an appointment"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Long) =
            handle4xx { apts.delete(id) }

}


