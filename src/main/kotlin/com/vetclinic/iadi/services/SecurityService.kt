package com.vetclinic.iadi.services

import com.vetclinic.iadi.config.ClientCustomInfo
import com.vetclinic.iadi.model.ClientRepository
import com.vetclinic.iadi.model.VeterinaryRepository
import org.springframework.stereotype.Service

@Service
class SecurityService(val clientRepository: ClientRepository, val veterinaryRepository: VeterinaryRepository) {

    fun isClient(principal: ClientCustomInfo, id: Long) : Boolean{
        val client = clientRepository.findById(id)
        return client.isPresent && client.get().username == principal.username
    }

    fun isVet(principal: ClientCustomInfo, id: Long) : Boolean{
        val vet = veterinaryRepository.findById(id)
        return vet.isPresent && vet.get().username == principal.username
    }
}