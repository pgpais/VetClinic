package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.RegisteredUserDAO
import com.vetclinic.iadi.model.VeterinarianDAO
import java.util.*

//TODO: add the other two parameters here
data class PetDTO(val id:Long, val name: String, val species: String) {
    constructor(pet: PetDAO) : this(pet.id, pet.name, pet.species)
}

data class PetAptsDTO(val petId:Long, val apts:List<AppointmentDTO>) {
    constructor(petDTO: PetDTO, map: List<AppointmentDTO>) : this(petDTO.id, map)
}

data class AppointmentDTO(val id:Long, val date: Date, val desc: String, var status:Boolean,
                          var reason:String){
    constructor(apt: AppointmentDAO) : this(apt.id, apt.date, apt.desc, apt.status, apt.reason)
}

data class UserDTO(val username:String, val password: String) // TODO: check if password makes sense

data class ClientDTO(val username:String, val password:String)

data class VeterinarianDTO(val vetId:Long, val name:String, val apts: List<AppointmentDTO>){
    constructor(vetDAO: VeterinarianDAO, apts: List<AppointmentDTO>) : this(vetDAO.vetId, vetDAO.name, apts)
}

data class AdminDTO(val name:String, val password:String)

data class RegisteredUserDTO(val Id:Long, val name: String, val apts: List<AppointmentDTO>, val pets: List<PetDTO>){
    constructor(user: RegisteredUserDAO, apts: List<AppointmentDTO>, pets: List<PetDTO>) : this(user.clientId, user.name, apts, pets)
}
