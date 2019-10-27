package com.vetclinic.iadi.model

import com.vetclinic.iadi.api.*
import java.time.LocalDateTime
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
        var physDesc: String,
        var healthDesc: String,
        var removed:Boolean = false
) {
    constructor(pet: PetDTO, owner: ClientDAO) : this(pet.id,pet.name,pet.species, pet.photo, owner, emptyList(), pet.chip, "", "", pet.deleted)
    constructor(pet: PetDTO, owner: ClientDAO, apts:List<AppointmentDAO>) : this(pet.id,pet.name,pet.species, pet.photo, owner, apts, pet.chip, "", "", pet.deleted)
    constructor(id: Long, name: String, species: String, photo: String, owner: ClientDAO, appointments: List<AppointmentDAO>, deleted: Boolean) :
            this(id, name, species, photo, owner, appointments, physDesc = "", healthDesc = "",removed = false)


    fun update(other: PetDAO) {
        this.name = other.name
        this.species = other.species
        this.appointments = other.appointments
    }
}

enum class AppointmentStatus(val status: Int) {
    PENDING(0),
    ACCEPTED(1),
    REJECTED(2),
    COMPLETED(3)
}

@Entity
data class AppointmentDAO(
        @Id @GeneratedValue val id:Long,
        var date: LocalDateTime,
        var desc:String,
        var status: AppointmentStatus,
        var reason:String,
        @ManyToOne(fetch = FetchType.LAZY)
        var pet: PetDAO,
        @ManyToOne(fetch = FetchType.LAZY)
        var client:ClientDAO,
        @ManyToOne(fetch=FetchType.LAZY)
        var vet: VeterinarianDAO
) {
    constructor(apt: AppointmentDTO, pet: PetDAO, user: ClientDAO, vet: VeterinarianDAO) : this(apt.id, apt.date, apt.desc, apt.status, apt.reason, pet, user, vet)

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
        override var username: String,
        override var pass:String,
        var photo: String,
        @OneToMany(mappedBy = "vet", fetch = FetchType.EAGER)
        var schedule:List<ShiftsDAO>,
        @OneToMany(mappedBy = "vet")
        var appointments: List<AppointmentDAO>
):RegisteredUsersDAO(id) {

    constructor(vet: VeterinarianDTO, schedule: List<ShiftsDAO>, apt: List<AppointmentDAO>) : this(vet.vetId, vet.name,vet.username, vet.pass, vet.photo, schedule, apt)

    fun update(other: VeterinarianDAO) {
        this.name = other.name
        this.appointments = other.appointments
    }
}
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class RegisteredUsersDAO (//TODO: change to username
        @Id @GeneratedValue open val id: Long) {

    abstract var name:String
    abstract var username: String
    abstract var pass: String
    //TODO: add rest of info
}

@Entity
data class ClientDAO(
        @Id @GeneratedValue override val id:Long,
        override var name:String,
        override var username:String,
        override var pass:String,
        @OneToMany(mappedBy = "owner")
        var pets:List<PetDAO>,
        @OneToMany(mappedBy = "client")
        var appointments: List<AppointmentDAO>) : RegisteredUsersDAO(id) {

    constructor(client:ClientDTO): this(client.id, client.name, client.username, client.pass, emptyList(), emptyList())
    constructor(client:ClientDTO, pets: List<PetDAO>): this(client.id, client.name, client.username, client.pass, pets, emptyList())
}

@Entity
data class AdminDAO(
        @Id @GeneratedValue override val id:Long,
        override  var name: String,
        override var username: String,
        override  var pass: String) : RegisteredUsersDAO(id) {
    constructor(admin: AdminDTO) : this(admin.id, admin.name, admin.username, admin.pass)
}

@Entity
data class ShiftsDAO(
        @Id @GeneratedValue val id:Long,
        var start:LocalDateTime, var end:LocalDateTime,
        @ManyToOne var vet: VeterinarianDAO){
    constructor(schedule:ShiftsDTO, vet: VeterinarianDAO): this(schedule.id, schedule.start, schedule.end, vet)
}

