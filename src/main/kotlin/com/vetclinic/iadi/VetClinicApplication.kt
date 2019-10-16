package com.vetclinic.iadi

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.AppointmentRepository
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.model.PetRepository
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
            apts: AppointmentRepository
    ) = CommandLineRunner {
        val pantufas = PetDAO(1L, "pantufas", "Dog", emptyList())
        val bigodes = PetDAO(2L, "bigodes", "Cat", emptyList())
        val petsDAO = mutableListOf(pantufas, bigodes);
        pets.saveAll(petsDAO)
        val apt = AppointmentDAO(1L, Date(), "consulta", pantufas)
        apts.save(apt)
    }
}


fun main(args: Array<String>) {
    runApplication<VetClinicApplication>(*args)
}
