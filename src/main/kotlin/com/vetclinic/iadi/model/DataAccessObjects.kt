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

import com.vetclinic.iadi.api.AppointmentDTO
import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.api.VeterinarianDTO
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import java.util.*
import javax.persistence.*

@Entity
data class PetDAO(
        @Id @GeneratedValue val id:Long,
        var name: String,
        var species: String,
        @OneToMany(mappedBy = "pet")
        var appointments:List<AppointmentDAO>
) {
    constructor() : this(0,"","", emptyList())

    constructor(pet: PetDTO, apts:List<AppointmentDAO>) : this(pet.id,pet.name,pet.species, apts)

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
        var status:Boolean,
        var reason:String,
        @ManyToOne
        var pet: PetDAO,
        @ManyToOne(fetch=FetchType.LAZY)
        @NotFound(action = NotFoundAction.IGNORE)
        var vet: VeterinarianDAO
) {
    constructor() : this(0, Date(), "", true, "", PetDAO(), VeterinarianDAO())
    constructor(apt: AppointmentDTO, pet: PetDAO, vet: VeterinarianDAO) : this(apt.id, apt.date, apt.desc, apt.status, apt.reason, pet, vet)

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
        @Id @GeneratedValue val vetId: Long,
        var name: String,
        @OneToMany(mappedBy = "vet")
        var appointments: List<AppointmentDAO>
) {
    constructor() : this(0, "", emptyList())
    constructor(vet: VeterinarianDTO, apt: List<AppointmentDAO>) : this(vet.vetId, vet.name, apt)

    fun update(other: VeterinarianDAO) {
        this.name = other.name
        this.appointments = other.appointments
    }
}



