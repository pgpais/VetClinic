package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.AppointmentDTO
import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.model.*
import org.springframework.stereotype.Service

@Service
class PetService(val pets: PetRepository, val appointments: AppointmentRepository, val vets:VeterinaryRepository, val clients:ClientRepository) {
    //var pet:PetDTO;

    /*fun getPetByID(id:Long):PetDAO =
            if(id == 1L)
                PetDAO(id, "ola", "cao", emptyList())
            else
                throw NotFoundException("Pet $id not found")
*/
    fun getPetById(id:Long) = pets.findByIdAndRemovedIsFalse(id).orElseThrow{NotFoundException("There is no Pet with Id $id")}

    fun getAllPets():Iterable<PetDAO> = pets.findAllByRemovedFalse()

    fun addNew(pet: PetDTO, ownerId:Long) {

        val owner = clients.findById(ownerId).orElseThrow { NotFoundException("There is no Client with Id $ownerId") }
        val petDAO = PetDAO(pet, owner)

        //if it exists
        if(petDAO.id != 0L){
            throw PreconditionFailedException("Id must be 0 on insertion")
        }
        else{
            pets.save(petDAO)
        }
    }



    fun getAppointments(id:Long) : List<AppointmentDAO>{
        val pet = pets.findByIdWithAppointment(id)
                .orElseThrow { NotFoundException("There is no Pet with Id $id") }
        return pet.appointments
    }

    fun newAppointment(id:Long, apt: AppointmentDTO) {
        val pet = getPetById(id)
        val vet = vets.findById(apt.vetId).orElseThrow{ NotFoundException("There is no Veterinarian with Id ${apt.vetId}")}
        val aptDAO = AppointmentDAO(apt, pet, pet.owner, vet)
        // defensive programming
        if (aptDAO.id != 0L)
            throw PreconditionFailedException("Id must be 0 in insertion")
        else
            appointments.save(aptDAO)
    }

    fun update(pet: PetDTO, id: Long) {
        getPetById(id).let { it.update(PetDAO(pet, it.owner)); pets.save(it) }
    }

    fun delete(id: Long) {

        pets.updateRemoved(id)
    }


}