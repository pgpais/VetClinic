package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.model.ClientRepository
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.PetRepository
import org.springframework.stereotype.Service

@Service
class RegisteredUserService(val pets: PetRepository) {

    fun getPet(username: String, id: Long) {

        val pet = pets.findPetByOwnerAndId(id, username)

        return pet

    }

    fun getAllPets(username: String){

        val pet = pets.findAllByOwner(username)
        return pet
    }


}