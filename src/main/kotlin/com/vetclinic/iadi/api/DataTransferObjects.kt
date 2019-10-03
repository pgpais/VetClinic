package com.vetclinic.iadi.api

data class PetDTO(val id:Number, val name: String, val species: String)

data class ClientDTO(val username:String, val password:String)

data class EmployeeDTO(val name:String)

data class AdminDTO(val name:String, val password:String)