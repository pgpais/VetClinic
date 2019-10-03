package com.vetclinic.iadi.api

//TODO: add the other two parameters here
data class PetDTO(val id:Number, val name: String, val species: String, val age:Number, val owner:String)

data class ClientDTO(val username:String, val password:String)

data class EmployeeDTO(val name:String)

data class AdminDTO(val name:String, val password:String)
