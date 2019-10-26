package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.ClientDTO
import com.vetclinic.iadi.api.handle4xx
import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.ClientDAO
import com.vetclinic.iadi.model.ClientRepository
import com.vetclinic.iadi.model.PetDAO
import org.springframework.stereotype.Service

@Service
class ClientService(val clientRepository: ClientRepository) {
    fun login(client: ClientDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun register(client: ClientDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getClientById(id:Long) = clientRepository.findById(id).orElseThrow{NotFoundException("Couldn't find client with id $id")}

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

    fun deleteClient(userId: Long): ClientDAO = handle4xx {
        clientRepository.findById(userId).orElseThrow { NotFoundException("There is no client with id $userId") } } //TODO: delete, not find (but launch exception?)
}