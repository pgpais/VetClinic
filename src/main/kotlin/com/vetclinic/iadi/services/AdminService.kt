package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.AdminDTO
import com.vetclinic.iadi.api.VeterinarianDTO
import com.vetclinic.iadi.model.AdminDAO
import com.vetclinic.iadi.model.VeterinarianDAO
import com.vetclinic.iadi.model.VeterinaryRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AdminService(val vets: VeterinaryRepository) {
    fun createAdmin(adminDTO: AdminDAO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun createVet(vet: VeterinarianDAO) : Optional<VeterinarianDAO> {

            val aVet = vets.findById(vet.id)

            return if ( aVet.isPresent )
                Optional.empty()
            else {
                vet.pass = BCryptPasswordEncoder().encode(vet.pass)
                Optional.of(vets.save(vet))
            }
    }

    fun deleteAdmin(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteVet(id: Long) {

        val vet = vets.findById(id).orElseThrow { NotFoundException ("Couldn't find vet with id $id") }
        vets.delete(vet)

    }

}