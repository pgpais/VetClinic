package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.*
import java.net.URL
import java.util.*

//TODO: add the other two parameters here
data class PetDTO(val id:Long, val name: String, val species: String, val photo: String, val ownerId:Long
) {

    constructor(pet: PetDAO) : this(pet.id, pet.name, pet.species, pet.photo, pet.owner.id)
}

data class PetAptsDTO(val petId:Long, val apts:List<AppointmentDTO>) {
}

data class AppointmentDTO(val id:Long, val date: Date, val desc: String, var status:String,
                          var reason:String, var clientID: Long, var vetId: Long){

    constructor(apt: AppointmentDAO) : this(apt.id, apt.date, apt.desc, apt.status, apt.reason, apt.client.id, apt.vet.id)
}

data class UserDTO(val username:String, val password: String) // TODO: check if password makes sense

data class ClientDTO(val id:Long, val username:String, val password:String)

data class ClientPetDTO(val clientID:Long, val petDTO: List<PetDTO>)


data class VeterinarianDTO(val vetId:Long, val name:String, val password: String, val photo: String, val schedule: List<ShiftsDAO>){


    constructor(vetDAO: VeterinarianDAO) : this(vetDAO.id,  vetDAO.name, vetDAO.pass, vetDAO.photo, vetDAO.schedule)
}

data class ShiftsDTO(val id: Long, val start:Date, val end:Date, val vetId: Long){

    constructor(shiftsDAO: ShiftsDAO) : this(shiftsDAO.id, shiftsDAO.start, shiftsDAO.end, shiftsDAO.vet.id)
}

data class VetShiftDTO(val vetId: Long, val shiftsDTO: List<ShiftsDTO>)

data class VetAptsDTO(val vetId: Long, val apts: List<AppointmentDTO>)

data class AdminDTO(val name:String, val password:String)

