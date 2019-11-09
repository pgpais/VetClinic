package com.vetclinic.iadi.services

import com.vetclinic.iadi.config.ClientCustomInfo
import com.vetclinic.iadi.model.ClientRepository
import org.springframework.stereotype.Service

@Service
class SecurityService(val clientRepository: ClientRepository) {

    fun isClient(principal: ClientCustomInfo, id: Long) : Boolean{
        val client = clientRepository.findById(id)
        return client.isPresent && client.get().username == principal.username
    }
}