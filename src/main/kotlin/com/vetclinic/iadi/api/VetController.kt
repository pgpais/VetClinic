package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.VetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Api(value = "VetClinic Management System - Veterinarian API",
        description = "Management operations of Veterinarians in the IADI 2019 Pet Clinic")

@RestController
@PreAuthorize("hasRole('ROLE_VET')")
@RequestMapping("/vets")
class VetController (val vets:VetService) {

    @ApiOperation(value = "Get a single Veterinarian", response = VeterinarianDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved Veterinarian"),
        ApiResponse(code = 401, message = "Your account is not allowed to access this resource"),
        ApiResponse(code = 403, message = "You're not allowed to view this resource"),
        ApiResponse(code = 404, message = "Could not find the Veterinarian you were looking for")
    ])
    @GetMapping("/{id}")
    fun getById(@PathVariable id:Long): VeterinarianDTO =
            handle4xx {
                VeterinarianDTO(vets.getVetbyId(id))
            }

    @ApiOperation(value = "Get all Veterinarians", response = VeterinarianDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved all Veterinarians"),
        ApiResponse(code = 401, message = "Your account is not allowed to access this resource"),
        ApiResponse(code = 403, message = "You're not allowed to view this resource"),
        ApiResponse(code = 404, message = "Could not find any Veterinarian")
    ])
    @GetMapping("")
    fun getAll(): List<VeterinarianDTO> =
            handle4xx {
                vets.getAllVets().map { VeterinarianDTO(it) }
            }

    @ApiOperation(value = "Get a list of the a Veterinarian's pending appointments", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of pending appointments"),
        ApiResponse(code = 404, message = "Provided Veterinarian not found"),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @GetMapping("/{id}/appointments/pending")
    fun getPendingAppointments(@PathVariable id:Long):List<AppointmentDTO> =
        handle4xx {
            vets.getPendingAppointments(id).map{AppointmentDTO(it)}
        }

    @ApiOperation(value = "Get a list of all appointments of a vet", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of all appointments"),
        ApiResponse(code = 404, message = "Provided Veterinarian not found"),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @GetMapping("/{id}/appointments")
    fun getAcceptedAppointments(@PathVariable id:Long):List<AppointmentDTO> =
            handle4xx {
                vets.getAppointments(id).map{AppointmentDTO(it)}
            }
/*
    @ApiOperation(value = "Accept a pending appointment", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Successfully accepted appointment"),
        ApiResponse(code = 404, message = "Provided pending appointment not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @PostMapping("/appointments/accept/{aptId}")
    fun acceptAppointment(@PathVariable aptId:Long){
        handle4xx {
            vets.acceptAppointment(aptId)}
        }


    @ApiOperation(value = "Reject a pending appointment", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully accepted appointment"),
        ApiResponse(code = 400, message = "Request malformed (maybe you're missing the reason?)"),
        ApiResponse(code = 404, message = "Provided pending appointment not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @PutMapping("/appointments/reject/{aptId}")
    fun rejectAppointment(@PathVariable aptId:Long, @RequestBody reason:String){
        handle4xx {
            vets.rejectAppointment(aptId,reason)}
    }

    @ApiOperation(value = "Complete an appointment", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully completed appointment"),
        ApiResponse(code = 404, message = "Provided pending appointment not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @PutMapping("/appointments/complete/{aptId}")
    fun completeAppointment(@PathVariable aptId:Long){
        handle4xx {
            vets.completeAppointment(aptId)}

    }
*/
    @ApiOperation(value = "Update an appointment status to completed, accepted or rejected", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully completed appointment"),
        ApiResponse(code = 404, message = "Provided pending appointment not found "),
        ApiResponse(code = 401, message = "You're not allowed to access this resource")

    ])
    @PutMapping("/appointments/{aptId}")
    fun updateAppointmentStatus(@PathVariable aptId:Long, @RequestParam mode:String, @RequestParam reason:String?){
        handle4xx {
            vets.updateAppointment(aptId,mode,reason)}

    }



    @ApiOperation(value = "Get a Veterinarian's schedule", response = VetShiftDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully updated a user's information"),
        ApiResponse(code = 404, message = "User not found"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("/schedule/{id}")
    fun getSchedule(@PathVariable id:Long) : VetShiftDTO =
        handle4xx {
            VetShiftDTO(VeterinarianDTO(vets.getVetbyId(id)),
                                        (vets.getSchedule(id).map{ ShiftsDTO(it)}))
    }

    @ApiOperation(value = "Set a Veterinarian's schedule", response = VetShiftDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully updated a Veterinarians's schedule"),
        ApiResponse(code = 404, message = "Veterinarian not found"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @PostMapping("/schedule")
    fun setSchedule(@PathVariable id:Long, @RequestBody shifts: List<ShiftsDTO>) =
            handle4xx {
                vets.setSchedule(id, shifts)
            }

    @PostMapping("")
    fun addNew(@RequestBody vet:VeterinarianDTO) {
        vets.createVet(vet)
    }

    @ApiOperation(value = "Update a vet", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully updated a vet"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The vet you tried to update was not found")
    ])
    @PutMapping("{id}")
    fun update(@PathVariable id:Long, @RequestBody vet: VeterinarianDTO){
        vets.update(id, vet)
    }

    //TODO: remove vet?
}