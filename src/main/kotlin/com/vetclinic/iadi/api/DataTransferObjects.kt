package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.AppointmentDAO
import java.util.*

//TODO: add the other two parameters here
data class PetDTO(val id:Long, val name: String, val species: String)

data class AppointmentDTO(val id:Long, val date: Date, val desc: String){
    constructor(apt: AppointmentDAO) : this(apt.id, apt.date, apt.desc)
}

data class ClientDTO(val username:String, val password:String)

data class EmployeeDTO(val name:String)

data class AdminDTO(val name:String, val password:String)
