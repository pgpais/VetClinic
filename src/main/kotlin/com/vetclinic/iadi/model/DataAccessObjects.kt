package com.vetclinic.iadi.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

//daos are for databases

@Entity
data class PetDAO(@Id @GeneratedValue val id:Long, val name:String, val species:String)