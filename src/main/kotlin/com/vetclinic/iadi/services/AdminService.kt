package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.AdminDTO
import com.vetclinic.iadi.api.VeterinarianDTO
import com.vetclinic.iadi.model.AdminDAO
import com.vetclinic.iadi.model.AdminRepository
import com.vetclinic.iadi.model.VeterinarianDAO
import com.vetclinic.iadi.model.VeterinaryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AdminService(val vets: VeterinaryRepository, val admins:AdminRepository) {
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

}