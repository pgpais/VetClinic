package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.AdminDTO
import com.vetclinic.iadi.api.VeterinarianDTO
import com.vetclinic.iadi.model.AdminRepository
import com.vetclinic.iadi.model.VeterinarianDAO
import com.vetclinic.iadi.model.VeterinaryRepository
import org.springframework.stereotype.Service

@Service
class AdminService(val vets: VeterinaryRepository, val admins:AdminRepository) {
    fun createAdmin(adminDTO: AdminDTO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createVet(vet: VeterinarianDTO) {

        val newVet = VeterinarianDAO(vet)
        vets.save(newVet)
    }

    fun deleteAdmin(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteVet(id: Long) {
        val vet = vets.findById(id).orElseThrow { NotFoundException ("Couldn't find vet with id $id") }
        vets.delete(vet)
    }

    fun getAdminById(id: Long) =
            admins.findById(id).orElseThrow{ NotFoundException("Couldn't find user with id $id") }

}