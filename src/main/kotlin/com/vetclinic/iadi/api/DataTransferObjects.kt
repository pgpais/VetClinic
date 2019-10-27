package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.*
import java.net.URL
import java.time.LocalDateTime
import java.util.*

//TODO: add the other two parameters here
data class PetDTO(val id:Long, val name: String, val species: String, val photo: String, val ownerId:Long, val chip: UUID, val deleted:Boolean
) {
    constructor(pet: PetDAO) : this(pet.id, pet.name, pet.species, pet.photo, pet.owner.id, pet.chip, pet.removed)
}

data class PetAptsDTO(val pet:PetDTO, val apts:List<AppointmentDTO>) {
}

data class AppointmentDTO(val id:Long, val date: LocalDateTime, val desc: String, var status:AppointmentStatus,
                          var reason:String, var petId: Long, var clientId: Long, var vetId: Long){

    constructor(apt: AppointmentDAO) : this(apt.id, apt.date, apt.desc, apt.status, apt.reason, apt.pet.id, apt.client.id, apt.vet.id)
}


data class UserDTO(val id: Long, val username:String, val pass:String){ // TODO: check if password makes sense
    constructor(user: RegisteredUsersDAO) : this(user.id, user.name, user.pass)
}

data class ClientDTO(val id:Long, val name:String, val username: String, val pass:String)

data class ClientPetDTO(val client:ClientDTO, val petDTO: List<PetDTO>)


data class VeterinarianDTO(val vetId:Long, val name:String, val username: String, val pass: String, val photo: String){


    constructor(vetDAO: VeterinarianDAO) : this(vetDAO.id,  vetDAO.name, vetDAO.username, vetDAO.pass, vetDAO.photo)
}

data class ShiftsDTO(val id: Long, val start: LocalDateTime, val end:LocalDateTime, val vetId: Long){

    constructor(shiftsDAO: ShiftsDAO) : this(shiftsDAO.id, shiftsDAO.start, shiftsDAO.end, shiftsDAO.vet.id)
}

data class VetShiftDTO(val vet:VeterinarianDTO, val shiftsDTO: List<ShiftsDTO>)

data class VetAptsDTO(val vet:VeterinarianDTO, val apts: List<AppointmentDTO>)

data class AdminDTO(val id: Long, val name:String, val username: String, val pass:String){

    constructor(admin: AdminDAO) : this(admin.id, admin.name, admin.username, admin.pass)
}