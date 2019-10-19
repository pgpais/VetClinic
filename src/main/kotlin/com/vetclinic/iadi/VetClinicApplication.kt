package com.vetclinic.iadi

import com.vetclinic.iadi.model.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.*

@SpringBootApplication
class VetClinicApplication {

    @Bean
    fun init(
            pets: PetRepository,
            apts: AppointmentRepository,
            vets: VeterinaryRepository
    ) = CommandLineRunner {
        val pantufas = PetDAO(1L, "pantufas", "Dog", emptyList())
        val bigodes = PetDAO(2L, "bigodes", "Cat", emptyList())

        //idk why it can't be 1 or 2
        val manel =  VeterinarianDAO(3, "manel", emptyList())
        val petsDAO = mutableListOf(pantufas, bigodes);
        pets.saveAll(petsDAO)
        vets.save(manel)

        val apt = AppointmentDAO(1L, Date(), "consulta",true, "", pantufas, manel)
        apts.save(apt)

        apts.updateStatusById(1,"i'm sick",false)
    }
}


fun main(args: Array<String>) {
    runApplication<VetClinicApplication>(*args)
}
