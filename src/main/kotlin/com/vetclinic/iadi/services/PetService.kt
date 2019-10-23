package com.vetclinic.iadi.services

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.AppointmentRepository
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(val pets: PetRepository, val appointments: AppointmentRepository) {
    //var pet:PetDTO;

    /*fun getPetByID(id:Long):PetDAO =
            if(id == 1L)
                PetDAO(id, "ola", "cao", emptyList())
            else
                throw NotFoundException("Pet $id not found")
*/
    fun getPetByID(id:Long) = pets.findById(id).orElseThrow{NotFoundException("There is no Pet with Id $id")}

    fun getAllPets():Iterable<PetDAO> = pets.findAll()

    fun addNew(pet: PetDAO) {

        //if it exists
        if(pet.id != 0L){
            throw PreconditionFailedException("Id must be 0 on insertion")
        }
        else{
            pets.save(pet)
        }
    }



    fun getAppointments(id:Long) : List<AppointmentDAO>{
        val pet = pets.findByIdWithAppointment(id)
                .orElseThrow { NotFoundException("There is no Pet with Id $id") }
        return pet.appointments
    }

    fun newAppointment(apt: AppointmentDAO) {
        // defensive programming
        if (apt.id != 0L)
            throw PreconditionFailedException("Id must be 0 in insertion")
        else
            appointments.save(apt)
    }

    fun update(pet: PetDAO, id: Long) {

        val oldPet = getPetByID(id)
        val newPet = PetDAO(oldPet.id, pet.name, pet.species, pet.photo, pet.owner, pet.appointments)

        pets.delete(oldPet)
        pets.save(newPet)


    }

    fun delete(id: Long) {

        val pet = getPetByID(id)
        pets.delete(pet)
    }


}