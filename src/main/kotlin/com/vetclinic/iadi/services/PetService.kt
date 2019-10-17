package com.vetclinic.iadi.services

import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(val pets: PetRepository) {
    //var pet:PetDTO;

    fun getPetByID(id:Long):PetDAO =
            if(id == 1L)
                PetDAO(id, "ola", "cao", emptyList())
            else
                throw NotFoundException("Pet $id not found")

    fun getAllPets():Iterable<PetDAO> = pets.findAll()

    fun addPet(pet: PetDAO) {

        //if it exists
        pet?.let { pets.save(PetDAO(0, it.name, it.species, emptyList())) }
    }


    //fun updatePet(oldId:Number, newId:Number) {
    //PetDTO oldPet = getPetByID(oldId)
    //return PetDTO (newId, oldPet.name, oldPet.species)
    //}



}