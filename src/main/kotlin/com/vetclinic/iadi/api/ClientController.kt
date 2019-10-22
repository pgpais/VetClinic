package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.ClientService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*
import javax.persistence.Entity

@Api(value="VetClinic Management System - Client API",
        description = "Management operations of Client in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/client")
class ClientController(client:ClientService){


    @ApiOperation(value="Get appointments of this user")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieve client's appointments"),
            ApiResponse(code = 404, message = "User does not exist")
    )
    @GetMapping("/apts/{userId}")
    fun getAppointments(@PathVariable userId:Long): List<AppointmentDTO>{

    }

    @PostMapping("/apts/{userId}")
    fun bookAppointment(@PathVariable userId:Long, @RequestBody apt:AppointmentDTO){
        //TODO: the client needs to choose a pet (which is chosen on appointment?)
    }

    @GetMapping("/pets/{userId}")
    fun getPets(@PathVariable userId: Long): PetDTO{

    }

    @PostMapping("/pets/{userId}")
    fun addPet(@PathVariable userId: Long, @RequestBody pet:PetDTO){

    }

    @DeleteMapping("/pets/{userId}/{petId}")
    fun deletePet(@PathVariable userId: Long, @PathVariable petId: Long){
        // TODO: remove pet without removing it
        // remove the ownerId?
    }
}