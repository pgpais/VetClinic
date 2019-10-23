package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.VetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*

@Api(value = "VetClinic Management System - Veterinarian API",
        description = "Management operations of Veterinarians in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/vets")
class VetController (val vets:VetService) {
    //TODO: "Veterinarians can also check the information of their clients, appointments, and of all pets"

    @ApiOperation(value = "Get a list of Pending appointments", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of pending appointments"),
        ApiResponse(code = 404, message = "Provided Veterinarian not found"),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @GetMapping("/appointments/pending/{id}")
    fun getPendingAppointments(@PathVariable id:Long):List<AppointmentDTO> =
        handle4xx {
            vets.getPendingAppointments(id).map{AppointmentDTO(it)}
        }

    @ApiOperation(value = "Get a list of all appointments by Id", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of all appointments"),
        ApiResponse(code = 404, message = "Provided Veterinarian not found"),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @GetMapping("/appointments/{id}") //can be used for Vet and Client (?)
    fun getAppointments(@PathVariable id:Long):List<AppointmentDTO> =
            handle4xx {
                vets.getAppointments(id).map{AppointmentDTO(it)}
            }

    @ApiOperation(value = "Accept a pending appointment")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully accepted appointment"),
        ApiResponse(code = 404, message = "Provided pending appointment not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @PostMapping("/appointments/accept/{aptId}")
    fun acceptAppointment(@PathVariable aptId:Long){ //TODO: add token to request
        handle4xx {
            vets.acceptAppointment(aptId)}
        }


    @ApiOperation(value = "Reject a pending appointment")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully accepted appointment"),
        ApiResponse(code = 400, message = "Request malformed (maybe you're missing the reason?)"),
        ApiResponse(code = 404, message = "Provided pending appointment not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @PostMapping("/appointments/reject/{aptId}")
    fun rejectAppointment(@PathVariable aptId:Long, @RequestBody reason:String){ //TODO: add token to request
        handle4xx {
            vets.rejectAppointment(aptId,reason)}
    }

    @ApiOperation(value = "Complete an appointment")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully completed appointment"),
        ApiResponse(code = 404, message = "Provided pending appointment not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @PostMapping("/appointments/complete/{aptId}")
    fun completeAppointment(@PathVariable aptId:Long){ //TODO: add token to request
        handle4xx {
            vets.completeAppointment(aptId)}

    }

}