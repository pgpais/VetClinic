package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.ClientDTO
import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.ClientDAO
import com.vetclinic.iadi.model.ClientRepository
import com.vetclinic.iadi.model.PetDAO
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientService(val clientRepository: ClientRepository) {
    fun login(client: ClientDTO) {

    }


    fun addClient(user: ClientDAO): Optional<ClientDAO> {
        val aUser = clientRepository.findById(user.id)

        return if (aUser.isPresent)
            Optional.empty()
        else {
            user.pass = BCryptPasswordEncoder().encode(user.pass)
            Optional.of(clientRepository.save(user))
        }
    }

    fun getClientById(id:Long) = clientRepository.findById(id).orElseThrow{NotFoundException("Couldn't find client with id $id")}

    fun getClientByUsername(username:String)  :Optional<ClientDAO> = clientRepository.findByUsername(username)

    fun getAppointments(userId: Long) : List<AppointmentDAO>{
        val client = clientRepository.findByIdWithAppointment(userId)
                .orElseThrow {NotFoundException("There is no client with id $userId")}
        return client.appointments
    }

    fun getPets(userId: Long): List<PetDAO> {
        val client = clientRepository.findByIdWithPets(userId)
                .orElseThrow {NotFoundException("There is no client with id $userId")}
        return client.pets
    }
}