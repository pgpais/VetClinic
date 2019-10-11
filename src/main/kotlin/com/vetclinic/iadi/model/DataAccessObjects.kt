package com.vetclinic.iadi.model

import javax.persistence.*

//daos are for databases

@Entity
data class PetDAO(@Id @GeneratedValue val id:Long, val name:String, val species:String)

@Entity
data class ClientDAO(@Id val username:String, val name:String, val password:String,
                     @OneToMany(mappedBy = "username") val pet:List<PetDAO>)