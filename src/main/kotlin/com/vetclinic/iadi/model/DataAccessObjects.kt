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
        @ManyToOne          var pet: PetDAO
) {
    constructor() : this(0, Date(),"", PetDAO())
    constructor(apt: AppointmentDTO, pet: PetDAO) : this(apt.id, apt.date, apt.desc, pet)
}


