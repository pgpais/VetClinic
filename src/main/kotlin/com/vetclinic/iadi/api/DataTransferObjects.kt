package com.vetclinic.iadi.api

import com.vetclinic.iadi.model.*
import java.net.URL
import java.time.LocalDateTime
import java.util.*

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


data class UserDTO(val id: Long, val username:String, val pass:String, val name:String, val photo: String, val email:String, val phone:Number, val address:String){
    constructor(user: RegisteredUsersDAO) : this(user.id, user.username, user.pass, user.name, user.photo, user.email, user.phone, user.address)
}

data class ClientDTO(val id:Long, val name:String, val username: String, val pass:String, val photo: String, val email: String, val phone: Number, val address: String){
    constructor(client: ClientDAO) : this(client.id, client.name, client.username, client.pass, client.photo, client.email, client.phone, client.address)
}

data class ClientPetDTO(val client:ClientDTO, val petDTO: List<PetDTO>)


data class VeterinarianDTO(val vetId:Long, val username: String, val pass: String,val name:String, val photo: String, val email: String, val phone: Number, val address: String, val frozen:Boolean,  val employeeID: UUID){


    constructor(vetDAO: VeterinarianDAO) : this(vetDAO.id, vetDAO.username, vetDAO.pass, vetDAO.name,  vetDAO.photo, vetDAO.email,vetDAO.phone, vetDAO.address, vetDAO.frozen, vetDAO.employeeID)
}

data class ShiftsDTO(val id: Long, val start: LocalDateTime, val end:LocalDateTime, val vetId: Long){

    constructor(shiftsDAO: ShiftsDAO) : this(shiftsDAO.id, shiftsDAO.start, shiftsDAO.end, shiftsDAO.vet.id)
}

data class VetShiftDTO(val vet:VeterinarianDTO, val shiftsDTO: List<ShiftsDTO>)

data class VetAptsDTO(val vet:VeterinarianDTO, val apts: List<AppointmentDTO>)

data class AdminDTO(val id: Long, val username: String, val pass:String, val name: String, val photo: String, val email: String, val phone: Number, val address: String, val employeeID: UUID){

    constructor(admin: AdminDAO) : this(admin.id,  admin.username, admin.pass, admin.name, admin.photo, admin.email, admin.phone, admin.address, admin.employeeID)
}