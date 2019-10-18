package com.vetclinic.iadi.model

import ch.qos.logback.core.net.server.Client
import com.vetclinic.iadi.api.AppointmentDTO
import com.vetclinic.iadi.api.PetDTO
import java.util.*
import javax.persistence.*

//daos are for databases

@Entity
data class PetDAO(@Id @GeneratedValue           val id:Long,
                                                var name:String,
                                                var species:String,

                                               // you don't have to explicitly send this variable in the constructor
                  @OneToMany(mappedBy = "pet")  var appointments:List<AppointmentDAO> = emptyList()
                 )
{constructor(): this(0, "","", emptyList())
constructor(pet:PetDTO): this(pet.id, pet.name, pet.species, emptyList())
    constructor(pet: PetDTO, emptyList: List<AppointmentDTO>) : this()

    fun update(other:PetDAO) {
        this.name = other.name
        this.species = other.species
        this.appointments = other.appointments
    }
}

@Entity
data class AppointmentDAO(
        @Id @GeneratedValue val id: Long,
                            val date: Date,
                            val desc: String,
        @ManyToOne          val pet: PetDAO)
{
    constructor() : this(0,Date(),"",PetDAO())
    constructor(apt: AppointmentDTO, pet: PetDAO) : this(apt.id, apt.date, apt.desc, pet)
}

/*
@Entity
data class ClientDAO(@Id                                val username:String,
                                                        val name:String,
                                                        val password:String,
                     @OneToMany(mappedBy = "id")  val pet:List<PetDAO>)

 */
data class ClientDAO(@Id val username:String, val name:String, val password:String,
                     @OneToMany(mappedBy = "username") val pet:List<PetDAO>)

@Entity
data class VeterinarianDAO(@Id @GeneratedValue val workerId:Long)