package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.AppointmentDTO
import com.vetclinic.iadi.api.ClientDTO
import com.vetclinic.iadi.api.handle4xx
import com.vetclinic.iadi.model.*
import org.springframework.stereotype.Service

@Service
class ClientService(val clientRepository: ClientRepository, val apts: AppointmentRepository, val vets:VeterinaryRepository, val clients: ClientRepository, val pets:PetRepository) {
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

    fun bookAppointment(apt: AppointmentDTO) {


        val vet = vets.findByIdAndFrozenIsFalse(apt.vetId).orElseThrow { NotFoundException("${apt.vetId} not found") }
        val pet = pets.findById(apt.petId).orElseThrow{NotFoundException("Pet not found ${apt.petId}")}
        val client = clients.findById(apt.clientId).orElseThrow { NotFoundException("Client not found ${apt.clientId}") }

        val apt = AppointmentDAO(apt, pet, client, vet)

        if (apt.id !=0L){
            throw PreconditionFailedException("id must be 0 on insert")
        }else{
        apt.status = AppointmentStatus.PENDING
        apts.save(apt)
        }
    }
}