package com.vetclinic.iadi.api

//TODO: add the other two parameters here
data class PetDTO(val id:Long, val name: String, val species: String)

data class ClientDTO(val username:String, val password:String)

