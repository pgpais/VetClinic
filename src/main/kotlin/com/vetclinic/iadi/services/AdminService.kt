package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.AdminDTO
import com.vetclinic.iadi.api.ShiftsDTO
import com.vetclinic.iadi.api.VeterinarianDTO
import com.vetclinic.iadi.api.handle4xx
import com.vetclinic.iadi.model.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AdminService(val vets: VeterinaryRepository, val admins: AdminRepository, val users: UserRepository, val clients: ClientRepository, val pets: PetRepository) {

    fun createAdmin(admin: AdminDTO) {
        val adminDAO = AdminDAO(admin)
        adminDAO.pass = BCryptPasswordEncoder().encode(admin.pass)
        if( adminDAO.id != 0L){
            throw PreconditionFailedException("Id must be 0 on insertion")
        } else
            admins.save(adminDAO)
    }

    fun createVet(vet: VeterinarianDTO) {
        val vetDAO = VeterinarianDAO(vet, emptyList(), emptyList())
        if( vetDAO.id != 0L){
            throw PreconditionFailedException("Id must be 0 on insertion")
        } else
            vets.save(vetDAO)
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

    fun setSchedule(id: Long, shifts: List<ShiftsDTO>) { //TODO: remove this (moved to VetService)
        val vet = getVetbyId(id)
        vet.schedule = shifts.map { ShiftsDAO(it, vet)}
        vets.save(vet)
    }

    fun getVetAppointments(vetId: Long) =
        vets.findByIdWithAppointment(vetId).orElseThrow{ NotFoundException("Couldn't find user with id $vetId") }.appointments


    fun getAdminByUsername(username:String)  : Optional<AdminDAO> = admins.findByUsername(username)

    fun update(id:Long, newAdminDTO: AdminDTO) {
        val oldAdminDAO = getAdminById(id)
        var newAdminDAO = AdminDAO(id, oldAdminDAO.username, newAdminDTO)
        admins.save(newAdminDAO)
    }


}