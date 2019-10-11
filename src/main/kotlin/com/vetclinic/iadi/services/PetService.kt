package com.vetclinic.iadi.services

import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.PetsRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class PetService(val pets:PetsRepository) {
    //var pet:PetDTO;

    fun getPetByID(id:Long):PetDAO =PetDAO(id, "ola", "cao")

    fun getAllPets():Iterable<PetDAO> = pets.findAll()

    fun addPet(pet: PetDAO) {

        //if it exists
        pet?.let { pets.save(PetDAO(0, it.name, it.species)) }
    }


    //fun updatePet(oldId:Number, newId:Number) {
    //PetDTO oldPet = getPetByID(oldId)
    //return PetDTO (newId, oldPet.name, oldPet.species)
    //}



}