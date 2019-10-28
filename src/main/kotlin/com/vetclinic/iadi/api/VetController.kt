package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.AppointmentStatus
import com.vetclinic.iadi.services.VetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@Api(value = "VetClinic Management System - Veterinarian API",
        description = "Management operations of Veterinarians in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/vets")
class VetController (val vets:VetService) {
    //TODO: "Veterinarians can also check the information of their clients, appointments, and of all pets"

    @ApiOperation(value = "Get a list of all appointments by Vet Id", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of all appointments"),
        ApiResponse(code = 404, message = "Provided Veterinarian not found"),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @GetMapping("/{id}/appointments")
    fun getAppointments(@PathVariable id:Long,
                        @RequestParam(required = false) startDate: String?,
                        @RequestParam(required = false) endDate: String?,
                        @RequestParam(required = false) status: AppointmentStatus?):List<AppointmentDTO> =
            handle4xx {
                if(status != null) {
                    vets.getAllAppointmentsWithStatus(id,status).map { AppointmentDTO(it) }

                }
                if(startDate != null && endDate != null) {
                    vets.getAllAppointmentsWithDate(id, startDate, endDate).map { AppointmentDTO(it) }
                }
                else
                {
                    vets.getAppointments(id).map{AppointmentDTO(it)}
                }
            }

    @ApiOperation(value = "Update appointment Status")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully accepted appointment"),
        ApiResponse(code = 404, message = "Provided appointment not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @PutMapping("/{id}/appointments/{aptid}")
    fun updateAppointment(@RequestBody (required =false) reason: String ,@RequestParam(required = false) status: AppointmentStatus?, @PathVariable id: Long, @PathVariable aptid: Long) { //TODO: add token to request
        handle4xx {
            if (status != null) {
                vets.updateAppointment(reason, status, id,aptid)
            }
        }
    }

    @ApiOperation(value = "Get vet full schedule")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of schedules"),
        ApiResponse(code = 404, message = "Provided Veterinarian not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @GetMapping("/{id}/schedule")
    fun getSchedule(@PathVariable id:Long) : VetShiftDTO =
            handle4xx {
                VetShiftDTO(VeterinarianDTO(vets.getVetbyId(id)),
                        (vets.getSchedule(id).map{ ShiftsDTO(it)}))
            }

}