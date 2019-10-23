package com.vetclinic.iadi

import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.model.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.net.URL
import java.time.Instant
import java.time.LocalDate
import java.util.*

@SpringBootApplication
class VetClinicApplication {

    @Bean
    @Profile("runtime")
    fun init(
            pets: PetRepository,
            apts: AppointmentRepository,
            users: ClientRepository,
            admins: AdminRepository,
            shifts: ShiftsRepository,
            vets: VeterinaryRepository
    ) = CommandLineRunner {

        val user = ClientDAO(1,"", BCryptPasswordEncoder().encode("batatas"), emptyList(), emptyList())
        users.save(user)

        val pantufas = PetDAO(2L, "pantufas", "Dog", "", user, emptyList())

        val manel =  VeterinarianDAO(4L, "manel","","" ,emptyList(), emptyList())

        pets.save(pantufas)

        val bigodes = PetDAO(3L, "bigodes", "Cat","",user, emptyList())

        pets.save(bigodes)

        vets.save(manel)

        val turnodas8 = ShiftsDAO(4L, Date.from(Instant.now()),Date.from(Instant.now()),manel)

        shifts.save(turnodas8)

        val apt = AppointmentDAO(1L, Date(), "consulta", AppointmentStatus.PENDING," ", pantufas, user, manel)

        apts.save(apt)

        apts.updateStatusById(1,"i'm sick",AppointmentStatus.REJECTED)

        apts.updateStatusById(1,"",AppointmentStatus.ACCEPTED)



        /*

        //idk why it can't be 1 or 2

        pets.saveAll(petsDAO)
        vets.save(manel)

        val apt = AppointmentDAO(1L, Date(), "consulta", true," ", pantufas, user, manel)
        apts.save(apt)

        apts.updateStatusById(1,"i'm sick",false)

 */
    }


}


fun main(args: Array<String>) {
    runApplication<VetClinicApplication>(*args)
}
