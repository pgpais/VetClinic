package com.vetclinic.iadi.model

import com.vetclinic.iadi.api.*
import java.net.URL
import java.util.*
import javax.persistence.*

@Entity
data class PetDAO(
        @Id @GeneratedValue val id: Long,
        var name: String,
        var species: String,
        var photo:String,
        @ManyToOne(fetch = FetchType.LAZY)
        var owner:ClientDAO,
        @OneToMany(mappedBy = "pet")
        var appointments: List<AppointmentDAO>,
        val chip: UUID = UUID.randomUUID(), //TODO: is this called on every constructor? can it be overwritten?
        var physDesc: String = "",
        var healthDesc: String = ""
) {
    constructor() : this(0,"","" , "", ClientDAO(),emptyList())

    constructor(pet: PetDTO, owner: ClientDAO, apts:List<AppointmentDAO>) : this(pet.id,pet.name,pet.species, pet.photo, owner, apts)

    fun update(other: PetDAO) {
        this.name = other.name
        this.species = other.species
        this.appointments = other.appointments
    }
}

@Entity
data class AppointmentDAO(
        @Id @GeneratedValue val id:Long,
        var date: Date,
        var desc:String,

        var status:String,
        var reason:String,

        @ManyToOne(fetch = FetchType.LAZY)
        var pet: PetDAO,

        @ManyToOne(fetch = FetchType.LAZY)
        var client:ClientDAO,

        @ManyToOne(fetch=FetchType.LAZY)
        var vet: VeterinarianDAO
) {
    constructor() : this(0, Date(), "", "pending", "",PetDAO(), ClientDAO(), VeterinarianDAO())
    constructor(apt: AppointmentDTO, pet: PetDAO, vet: VeterinarianDAO) : this(apt.id, apt.date, apt.desc, apt.status, apt.reason, pet, pet.owner, vet)

    fun update(other: AppointmentDAO) {
        this.date = other.date
        this.desc = other.desc
        this.status = other.status
        this.reason = other.reason
        this.pet = other.pet
        this.vet = other.vet
    }
}

@Entity
data class VeterinarianDAO(
        @Id @GeneratedValue override val id: Long,
        override var name: String,
        override var pass:String,
        var photo: String,
        @OneToMany(mappedBy = "vet")
        var schedule:List<ShiftsDAO>,
        @OneToMany(mappedBy = "vet")
        var appointments: List<AppointmentDAO>
):RegisteredUsersDAO() {

    constructor() : this(0, "","","", emptyList(), emptyList())
    constructor(vet: VeterinarianDTO, apt: List<AppointmentDAO>) : this(vet.vetId, vet.name, vet.password, vet.photo, vet.schedule, apt)
    constructor(vet: VeterinarianDTO):this(vet.vetId, vet.name, vet.password, vet.photo, vet.schedule, emptyList())

    fun update(other: VeterinarianDAO) {
        this.name = other.name
        this.appointments = other.appointments
    }
}
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class RegisteredUsersDAO {

    abstract val id: Long
    abstract var name: String
    abstract var pass: String
}

@Entity
data class ClientDAO (
        @Id @GeneratedValue override val id:Long,
        override var name:String,
        override var pass:String,
        @OneToMany(mappedBy = "owner")
        var pets:List<PetDAO>) : RegisteredUsersDAO() {

    constructor(): this(0, "", "", emptyList())
    constructor(client:ClientDTO, pets: List<PetDAO>): this(client.id, client.username, client.password, pets)
}

@Entity
data class AdminDAO(
        @Id @GeneratedValue override val id:Long,
        override  var name: String,
        override  var pass: String) : RegisteredUsersDAO()

@Entity
data class ShiftsDAO(
        @Id @GeneratedValue val id:Long,
        var start:Date, var end:Date,
        @ManyToOne var vet: VeterinarianDAO){

    constructor(): this(0, Date(), Date(),VeterinarianDAO())
    constructor(schedule:ShiftsDTO, vet: VeterinarianDAO): this(schedule.id, schedule.start, schedule.end, vet)
}

