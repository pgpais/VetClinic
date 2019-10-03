package com.vetclinic.iadi.api

import com.vetclinic.iadi.services.PetService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.*

@Api(value="VetClinic Management System - Pet API",
        description = "Management operations of Pets in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/pets")
class PetController(val service: PetService) {

    @ApiOperation(value="View a list of registered pets", response = PetDTO::class, responseContainer = "List")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of pets"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("")
    fun getAllPets() = emptyList<PetDTO>()

    @ApiOperation(value="Get the details of a single pet", response = PetDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved pet"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        ApiResponse(code = 404, message = "This is not the resource you are looking for - MindTrick.jpg")
    ])
    @GetMapping("/{id}")
    fun getOnePet(@PathVariable id:Number) = PetDTO(1, "Pantufas", "Dog", 8, "Me")

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

    }

}