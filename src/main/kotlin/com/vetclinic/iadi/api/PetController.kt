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

@Api(value="VetClinic Management System - Pet API",
        description = "Management operations of Pets in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/pets")
class PetController(val PetSer: PetService) {


    @ApiOperation(value="View a list of registered pets", response = List::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of pets"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])

    //from dao to dto
    @GetMapping("")
    fun getAllPets():Iterable<PetDTO> = PetSer.getAllPets().map { PetDTO(it.id, it.name, it.species) }

    @ApiOperation(value="Get the details of a single pet", response = PetDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved pet"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/{id}")
    fun getOnePet(@PathVariable id:Long) = PetSer.getPetByID(id).let { PetDTO(it.id,it.name,it.species) }

    @ApiOperation(value="Add a new pet to the database")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully added a pet"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg"),
        ApiResponse(code = 409, message = "The pet you tried to add already exists")
    ])
    @PostMapping("")
    fun addOnePet(@RequestBody pet:PetDTO) {
        PetSer.addPet(PetDAO(pet.id,pet.name,pet.species, emptyList()))
    }

    @GetMapping("/{id}/appointments")
    fun appointmentsOfPet(@PathVariable id:Long) : List<AppointmentDTO> =
            handle404 { PetSer.appointmentOfPet(id).map{ AppointmentDTO(it) } }

    @PostMapping("/(id)/appointments")

    fun newAppointment (@PathVariable id:Long, @RequestBody apt : AppointmentDTO) =
            handle404 { PetSer.getPetByID(id).let{PetSer.newAppointment(id, AppointmentDAO(apt, it))} }
}

