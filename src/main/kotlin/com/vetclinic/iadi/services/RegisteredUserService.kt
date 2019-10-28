package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.api.UserDTO
import com.vetclinic.iadi.model.*
import org.springframework.stereotype.Service

@Service
class RegisteredUserService(val pets: PetRepository, val userRepository: UserRepository) {

    //TODO: this might make all other values null (check it)
    fun updateInfo(username: String, newUser: UserDTO) {
        var oldUserDAO: RegisteredUsersDAO = userRepository.findByUsername(username).orElseThrow { NotFoundException("Can't find User with username $username") }
        var newUserDAO: RegisteredUsersDAO = RegisteredUsersDAO(oldUserDAO.id, oldUserDAO.username, newUser.pass, newUser.name, newUser.photo, newUser.email, newUser.phone, newUser.address)
        userRepository.save(newUserDAO)
    }
}
