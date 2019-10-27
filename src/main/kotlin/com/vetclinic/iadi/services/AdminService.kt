package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.handle4xx
import com.vetclinic.iadi.model.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service

@Service
class AdminService(val vets: VeterinaryRepository, val admins: AdminRepository, val users: UserRepository, val clients: ClientRepository, val pets: PetRepository) {

    fun createAdmin(admin: AdminDAO) {
        admins.save(admin)
    }

    fun createVet(vet: VeterinarianDAO) {
        vets.save(vet)
    }

    fun deleteAdmin(id: Long) {
        val admin = admins.findById(id).orElseThrow { NotFoundException("Couldn't find admin") }
        admins.delete(admin)
    }

    fun deleteVet(id: Long) {
        val vet = vets.findById(id).orElseThrow { NotFoundException ("Couldn't find vet with id $id") }
        vets.delete(vet)
    }

    fun getAdminById(id: Long) =
        admins.findById(id).orElseThrow{ NotFoundException("Couldn't find user with id $id") }

    fun getUserById(id: Long) =
        users.findById(id).orElseThrow { NotFoundException("Couldn't find user with id $id") }

    fun getClientById(id: Long) =
        clients.findById(id).orElseThrow{ NotFoundException("Couldn't find user with id $id") }

    fun getPetById(id: Long) =
        pets.findById(id).orElseThrow { NotFoundException("Couldn't find user with id $id") }


    fun getVetbyId(vetId: Long) =
        vets.findById(vetId).orElseThrow { NotFoundException("Couldn't find user with id $vetId") }

    fun addShift(vet: VeterinarianDAO, shiftsDAO: List<ShiftsDAO>) {
        vet.schedule = shiftsDAO
        vets.save(vet)
    }

    fun getVetAppointments(vetId: Long) =
        vets.findByIdWithAppointment(vetId).orElseThrow{ NotFoundException("Couldn't find user with id $vetId") }.appointments
    



}