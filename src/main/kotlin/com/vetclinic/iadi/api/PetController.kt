package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.services.PetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*
import pt.unl.fct.di.iadi.vetclinic.api.handle404


@Api(value = "VetClinic Management System - Pet API",
        description = "Management operations of Pets in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/pets")
class PetController(val pets: PetService) {

    @ApiOperation(value = "View a list of registered pets", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("")
    fun getAllPets() : List<PetAptsDTO> =
            pets.getAllPets().map { PetAptsDTO(PetDTO(it),
                    it.appointments.map { AppointmentDTO(it) }) }

    @ApiOperation(value = "Add a new pet", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully added a pet"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @PostMapping("")
    fun addNewPet(@RequestBody pet: PetDTO) =
            pets.addNew(PetDAO(pet, emptyList()))

    @ApiOperation(value = "Get the details of a single pet by id", response = PetDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved pet details"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    ])
    @GetMapping("/{id}")
    fun getOnePet(@PathVariable id:Long) : PetAptsDTO =
            handle404 { pets.getPetByID(id).let { PetAptsDTO(PetDTO(it), it.appointments.map { AppointmentDTO(it) }) } }

    @ApiOperation(value = "Update a pet", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully updated a pet"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @PutMapping("/{id}")
    fun updatePet(@RequestBody pet: PetDTO, @PathVariable id: Long) =
            handle404 { pets.update(PetDAO(pet, emptyList()), id) }

    @ApiOperation(value = "Delete a pet", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully deleted a pet"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @DeleteMapping("/{id}")
    fun deletePet(@PathVariable id: Long) =
            handle404 { pets.delete(id) }

    @ApiOperation(value = "Add a new appointment to a pet", response = Unit::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully added an appointment to a pet"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    ])
    @PostMapping("/{id}/appointments")
    fun newAppointment(@PathVariable id:Long,
                       @RequestBody apt:AppointmentDTO) =
            handle404 {
                val pet = pets.getPetByID(id)
                pets.newAppointment(id, AppointmentDAO(apt, pet,vet))
            }

    @ApiOperation(value = "Get a list of a pet's appointments", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of pet's appointments"),
        ApiResponse(code = 404, message = "Pet not found"),
        ApiResponse(code = 401, message = "You are not authorized to use this resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("/{id}/appointments")
    fun appointmentsOfPet(@PathVariable id:Long) : List<AppointmentDTO> =
            handle404 { pets.getAppointments(id).map{ AppointmentDTO(it) } }
}

