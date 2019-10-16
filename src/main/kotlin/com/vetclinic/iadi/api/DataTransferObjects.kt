package com.vetclinic.iadi.api

import java.util.*

//TODO: add the other two parameters here
data class PetDTO(val id:Long, val name: String, val species: String)

data class AppointmentDTO(val id:Long, val date: Date, val desc: String)

data class ClientDTO(val username:String, val password:String)

data class EmployeeDTO(val name:String)

data class AdminDTO(val name:String, val password:String)
