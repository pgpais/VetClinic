package com.vetclinic.iadi

import com.vetclinic.iadi.model.*
import com.vetclinic.iadi.services.VetService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@SpringBootApplication
class VetClinicApplication {

    @Bean
    fun init(
            pets: PetRepository,
            apts: AppointmentRepository,
            clients: ClientRepository,
            admins: AdminRepository,
            shifts: ShiftsRepository,
            vets: VeterinaryRepository,
            vetService: VetService
    ) = CommandLineRunner {

        val user = ClientDAO(1,"pedro","client","", emptyList(), emptyList())
        clients.save(user)

        val pantufas = PetDAO(2L, "pantufas", "Dog", "", user, emptyList(), false)

        val manel =  VeterinarianDAO(4L, "manel","doctor123","","",emptyList(), emptyList())

        pets.save(pantufas)

        val bigodes = PetDAO(3L, "bigodes", "Cat","",user, emptyList(), false)

        pets.save(bigodes)

        vets.save(manel)


        val admin = AdminDAO(3, "francisco", "Admin", "secret")

        admins.save(admin)

        val turnodas8 = ShiftsDAO(4L, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4),manel)

        shifts.save(turnodas8)

        val apt = AppointmentDAO(1L, LocalDateTime.now(), "consulta", AppointmentStatus.PENDING," ", pantufas, user, manel)

        apts.save(apt)

        apts.updateStatusById(1,"i'm sick",AppointmentStatus.REJECTED)

        apts.updateStatusById(1,"",AppointmentStatus.ACCEPTED)



       //vetService.addShift(manel.id, turnodas8)

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
