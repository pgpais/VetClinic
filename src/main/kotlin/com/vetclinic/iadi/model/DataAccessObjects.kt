/**
Copyright 2019 João Costa Seco

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.vetclinic.iadi.model

import com.vetclinic.iadi.api.*
import java.net.URL
import java.util.*
import javax.persistence.*

@Entity
data class PetDAO(
        @Id @GeneratedValue val id:Long,
        var name: String,
        var species: String,
        var photo:URL,
        @ManyToOne(fetch = FetchType.LAZY)
        var owner:ClientDAO,
        @OneToMany(mappedBy = "pet")
        var appointments:List<AppointmentDAO>

) {
    constructor() : this(0,"","" , URL(""), ClientDAO(),emptyList())

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
        @Id @GeneratedValue val id: Long,
        var name: String,
        var photo: URL,
        var schedule:List<Pair<Date, Date>>,
        @OneToMany(mappedBy = "vet")
        var appointments: List<AppointmentDAO>
) {
    constructor() : this(0, "",URL(""), emptyList(), emptyList())
    constructor(vet: VeterinarianDTO, apt: List<AppointmentDAO>) : this(vet.vetId, vet.name, vet.photo, vet.schedule, apt)
    constructor(vet: VeterinarianDTO):this(vet.vetId, vet.name, vet.photo, vet.schedule, emptyList())

    fun update(other: VeterinarianDAO) {
        this.name = other.name
        this.appointments = other.appointments
    }
}

@Entity
data class RegisteredUserDAO(
        @Id @GeneratedValue val id: Long,
        var name: String,
        var password: String){

    constructor() : this(0, "", "")
    constructor(user: RegisteredUserDTO) : this(user.Id,user.name, user.password)
}

@Entity
data class ClientDAO(
        @Id @GeneratedValue val id:Long,
        var name:String,
        var pass:String,
        @OneToMany(mappedBy = "owner")
        var pets:List<PetDAO>){

    constructor(): this(0, "", "", emptyList())
    constructor(client:ClientDTO, pets: List<PetDAO>): this(client.id, client.username, client.password, pets)
}




