package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.AppointmentDTO
import com.vetclinic.iadi.api.ClientDTO
import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.api.handle4xx
import com.vetclinic.iadi.model.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientService(val clientRepository: ClientRepository, val apts: AppointmentRepository, val vets:VeterinaryRepository, val clients: ClientRepository, val pets:PetRepository) {
    fun login(client: ClientDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun register(client: ClientDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getClientByUsername(username:String)   = clientRepository.findByUsername(username).orElseThrow{NotFoundException("Couldn't find client with username $username")}

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

    fun getPets(username: String): List<PetDAO> {
        val client = clientRepository.findByUsernameWithPets(username)
                .orElseThrow {NotFoundException("There is no client with id $username")}
        return client.pets;
    }


    fun deleteClient(userId: Long) = handle4xx {
        val client: ClientDAO = clientRepository.findById(userId).orElseThrow { NotFoundException("There is no client with id $userId") }
        clientRepository.delete(client)
    }

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

    fun deleteClientsPet(userId: Long, petId: Long) {

        val owner = getClientById(userId)
        pets.removeByIdAndUserId(owner, petId)
    }

    fun createPet(userId: Long, pet: PetDTO) {
        val owner = getClientById(userId)
        val petDAO = PetDAO(pet, owner)
        if(petDAO.id != 0L)
            throw PreconditionFailedException("id must be 0 on insert")
        else
            pets.save(petDAO)
    }

    fun update(username: String, client: ClientDTO) {
        val clientDAO = getClientByUsername(username)
        val newClientDAO = ClientDAO(0, clientDAO.username, clientDAO.pets, clientDAO.appointments, client)

        clients.save(newClientDAO)
    }
}