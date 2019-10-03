package com.vetclinic.iadi.api

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value="VetClinic Management System - Registered Client API",
        description = "Management operations of Registered Clients in the IADI 2019 Pet Clinic")

@RestController
@RequestMapping("/client")
class RegClientController {

    @ApiOperation(value="View a list of this client's pets", response = PetDTO::class)
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of pets"),
        ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden")
    ])
    @GetMapping("/pets")
    fun CheckPets() = emptyList<PetDTO>()
}