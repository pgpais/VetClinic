package com.vetclinic.iadi.services

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.PetRepository
import org.springframework.stereotype.Service

import org.springframework.web.client.HttpClientErrorException
import java.util.*

@Service
class PetService(val pets: PetRepository) {
    //var pet:PetDTO;

    /*fun getPetByID(id:Long):PetDAO =
            if(id == 1L)
                PetDAO(id, "ola", "cao", emptyList())
            else
                throw NotFoundException("Pet $id not found")
*/
    fun getPetByID(id:Long) = pets.findById(id).orElseThrow{NotFoundException("There is no Pet with Id $id")}

    fun getAllPets():Iterable<PetDAO> = pets.findAll()

    fun addPet(pet: PetDAO) {

        //if it exists
        pet?.let { pets.save(PetDAO(0, it.name, it.species, emptyList())) }
        pets.save(pet)

    }

    fun appointmentOfPet(id:Long) : List<AppointmentDAO>{
        val pet: Optional<PetDAO> = pets.findById(id)
        return pet.appointments


    }

    fun newAppointmentOfPet(id: Long, appointment: AppointmentDAO) {
        val pet: PetDAO = getPetByID(id)

        appointment.pet = pet
        appointments.save(appointment)

        pet.appointments = pet.appointments.plus(appointment)
        pets.save(pet)
    }



}