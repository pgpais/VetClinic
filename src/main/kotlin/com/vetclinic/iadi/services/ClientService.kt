package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.ClientDTO
import com.vetclinic.iadi.model.ClientDAO
import com.vetclinic.iadi.model.ClientRepository
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
}