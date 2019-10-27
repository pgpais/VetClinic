package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.api.UserDTO
import com.vetclinic.iadi.model.*
import org.springframework.stereotype.Service

@Service
class RegisteredUserService(val pets: PetRepository, val userRepository: UserRepository) {

    fun getPet(username: String, id: Long) {

        val pet = pets.findPetByOwnerAndId(id, username)

        return pet

    }

    fun getAllPets(username: String){

        val pet = pets.findAllByOwner(username)
        return pet
    }

    fun updateInfo(username: String, newUser: UserDTO) {


        }
    }
