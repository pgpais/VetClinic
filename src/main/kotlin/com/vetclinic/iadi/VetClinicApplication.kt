package com.vetclinic.iadi

import com.vetclinic.iadi.api.AdminDTO
import com.vetclinic.iadi.api.UserDTO
import com.vetclinic.iadi.model.*
import com.vetclinic.iadi.services.AdminService
import com.vetclinic.iadi.services.RegisteredUserService
import com.vetclinic.iadi.services.SecurityService
import com.vetclinic.iadi.services.VetService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
            vetService: VetService,
            userService: RegisteredUserService,
            adminService:AdminService
    ) = CommandLineRunner {

        // BASE ADMIN ACCOUNT
        val baseAdmin = AdminDAO(0L, "BASEADMIN", BCryptPasswordEncoder().encode("ADMIN"), "BASEADMIN","","",6,"")
        admins.save(baseAdmin)

        val user = ClientDAO(0L,"Client123", BCryptPasswordEncoder().encode("123"), "manel","","",6,"",emptyList(), emptyList())

        val user2 = ClientDAO(0L,"Client124", BCryptPasswordEncoder().encode("123"), "manel","","",6,"",emptyList(), emptyList())

        val vet = VeterinarianDAO(0L,"VET123", BCryptPasswordEncoder().encode("123"),"Joaquina" ,"www.google.com", "",7,"",emptyList(), emptyList())

        val pantufas = PetDAO(0L, "pantufas", "Dog", "www.google.com", user, emptyList(),false)
        val bigodes = PetDAO(0L, "bigodes", "Cat", "www.google.com", user2, emptyList(),false)
        val petsDAO = ArrayList(listOf(pantufas, bigodes))

        clients.save(user)
        clients.save(user2)
        pets.save(pantufas)
        pets.save(bigodes)
        vets.save(vet)

        val appointmentDAO = AppointmentDAO(0, LocalDateTime.now().toString(),"", AppointmentStatus.PENDING,"", pantufas, user, vet)
        apts.save(appointmentDAO)
    }


}


fun main(args: Array<String>) {
    runApplication<VetClinicApplication>(*args)
}
