package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.VeterinarianDAO
import com.vetclinic.iadi.services.AppointmentService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*
import pt.unl.fct.di.iadi.vetclinic.api.handle404

@Api(value = "VetClinic Management System - Appointment API",
        description = "Management operations of Appointments in the IADI 2019 Pet Clinic")


@RestController
@RequestMapping("/appointments")
class AppointmentController(val apts: AppointmentService) {

    @ApiOperation(value = "View a list of appointments", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("")
    fun getAllAppointments() : List<AppointmentDTO> =
            apts.getAllAppointments().map { AppointmentDTO(it.id, it.date, it.desc, it.status, it.reason) }

    @ApiOperation(value = "Get the details of an appointment by Id", response = AppointmentDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved pet details"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    ])
    @GetMapping("/{id}")
    fun getOneAppointment(@PathVariable id:Long) : AppointmentDTO =
            handle404 { apts.getAppointmentByID(id).let { AppointmentDTO(it.id, it.date, it.desc, it.status, it.reason) } }


    @ApiOperation(value = "Add an appointment", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully added an appointment"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @PostMapping("")
    fun newAppointment(@RequestBody apt: AppointmentDTO, @RequestBody pet: PetDAO, @RequestBody vet: VeterinarianDAO) =
            handle404 { apts.newAppointment(AppointmentDAO(apt, pet, vet))}

    @ApiOperation(value = "Delete an appointment", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully deleted an appointment"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @DeleteMapping("/{id}")
    fun deleteAppointment(@PathVariable id: Long) =
            handle404 { apts.delete(id) }

}


