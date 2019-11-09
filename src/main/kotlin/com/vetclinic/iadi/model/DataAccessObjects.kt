package com.vetclinic.iadi.model

import com.vetclinic.iadi.api.*
import org.hibernate.validator.constraints.UniqueElements
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

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
        val chip: UUID = UUID.randomUUID(),
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
@DiscriminatorValue("Vet")

data class VeterinarianDAO(
        @Id @GeneratedValue override val id: Long,
        @UniqueElements
        override var username: String,
        override var pass:String,

        override  var name: String,

        @NotBlank
        override var photo: String,
        override var email:String,
        override var phone:Number,
        override var address:String,


        @OneToMany(mappedBy = "vet", fetch = FetchType.EAGER)
        var schedule:List<ShiftsDAO>,
        @OneToMany(mappedBy = "vet")
        var appointments: List<AppointmentDAO>,

        var frozen:Boolean = false,
        @UniqueElements
        val employeeID: UUID = UUID.randomUUID()


        ):RegisteredUsersDAO(id, username, pass, name, photo, email, phone, address) {

    constructor(vet: VeterinarianDTO, schedule: List<ShiftsDAO>, apt: List<AppointmentDAO>) : this(vet.vetId,vet.username, vet.pass, vet.name, vet.photo, vet.email, vet.phone, vet.address ,schedule, apt, vet.frozen, vet.employeeID)
    constructor(id: Long, username: String, vet: VeterinarianDTO, schedule: List<ShiftsDAO>, apt: List<AppointmentDAO>) : this(id, username, vet.pass, vet.name, vet.photo, vet.email, vet.phone, vet.address, schedule, apt, vet.frozen, vet.employeeID)

    fun update(other: VeterinarianDAO) {
        this.name = other.name
        this.appointments = other.appointments
    }
}
@Entity
@DiscriminatorColumn(name = "USER_ROLE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
open class RegisteredUsersDAO (//TODO: change to username
        @Id @GeneratedValue open val id: Long,

        open var username: String,
        open var pass: String,
        open var name:String,

        open var photo:String,
        open var email:String,
        open var phone:Number,
        open var address:String

){
    fun getDiscriminatorValue() = this.javaClass.name
}

@Entity
@DiscriminatorValue("Client")
data class ClientDAO(
        @Id @GeneratedValue override val id:Long,

        override var username:String,
        override var pass:String,

        override var name:String,
        override var photo: String,
        override var email:String,
        override var phone:Number,
        override var address:String,

        @OneToMany(mappedBy = "owner")
        var pets:List<PetDAO>,
        @OneToMany(mappedBy = "client")
        var appointments: List<AppointmentDAO>) : RegisteredUsersDAO(id,username,pass, name, photo, email, phone, address) {

    constructor(client:ClientDTO): this(client.id, client.username, client.pass,client.name, client.photo, client.email, client.phone,client.address,emptyList(), emptyList())
    constructor(client:ClientDTO, pets: List<PetDAO>): this(client.id, client.username, client.pass,client.name,client.photo, client.email,client.phone,client.address,pets, emptyList())
    constructor(id: Long, username: String, pets: List<PetDAO>, appointments: List<AppointmentDAO>, client: ClientDTO) : this(id, username, client.pass,client.name,client.photo, client.email,client.phone,client.address,pets, appointments)
}

@Entity
@DiscriminatorValue("Admin")
data class AdminDAO(
        @Id @GeneratedValue override val id:Long,
        @UniqueElements
        override var username: String,
        override  var pass: String,

        override  var name: String,
        @NotBlank
        override var photo: String,
        override var email:String,
        override var phone:Number,
        override var address:String,

        @UniqueElements
        val employeeID: UUID = UUID.randomUUID()


        ) : RegisteredUsersDAO(id, username, pass, name, photo, email, phone, address) {
    constructor(admin: AdminDTO) : this(admin.id, admin.username, admin.pass, admin.name,admin.photo,admin.email,admin.phone,admin.address)
    constructor(id: Long, username: String, admin: AdminDTO) : this(id, username, admin.pass, admin.name, admin.photo, admin.email, admin.phone, admin.address)
}

@Entity
data class ShiftsDAO(
        @Id @GeneratedValue val id:Long,
        var start:LocalDateTime, var end:LocalDateTime,
        @ManyToOne var vet: VeterinarianDAO){
    constructor(schedule:ShiftsDTO, vet: VeterinarianDAO): this(schedule.id, schedule.start, schedule.end, vet)
}

