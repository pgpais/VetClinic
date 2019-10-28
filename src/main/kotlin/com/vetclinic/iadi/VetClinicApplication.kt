package com.vetclinic.iadi

import com.vetclinic.iadi.api.AdminDTO
import com.vetclinic.iadi.api.UserDTO
import com.vetclinic.iadi.model.*
import com.vetclinic.iadi.services.AdminService
import com.vetclinic.iadi.services.RegisteredUserService
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
        val baseAdmin = AdminDAO(1L, "BASEADMIN", BCryptPasswordEncoder().encode("ADMIN"), "BASEADMIN","","",6,"")
        admins.save(baseAdmin)
    }


}


fun main(args: Array<String>) {
    runApplication<VetClinicApplication>(*args)
}
