package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.api.UserDTO
import com.vetclinic.iadi.model.*
import org.springframework.stereotype.Service

@Service
class RegisteredUserService(val pets: PetRepository, val userRepository: UserRepository) {

    fun updateInfo(username: String, newUser: UserDTO) {



        }
    }
