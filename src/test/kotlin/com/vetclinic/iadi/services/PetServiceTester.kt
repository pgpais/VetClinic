package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.controllers.PetControllerTester
import com.vetclinic.iadi.model.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class PetServiceTester {

    @Autowired
    lateinit var pets: PetService

    @MockBean
    lateinit var repo: PetRepository

    @MockBean
    lateinit var aptRepository: AppointmentRepository

    companion object Constants{

        val user = ClientDAO(4L,"Client123", "123", "manel","","",6,"",emptyList(), emptyList())

        val vet = VeterinarianDAO(5L,"VET123", "123","Joaquina" ,"www.google.com", "",7,"",emptyList(), emptyList())

        val pantufas = PetDAO(1L, "pantufas", "Dog", "www.google.com", user, emptyList(),false)
        val bigodes = PetDAO(2L, "bigodes", "Cat", "www.google.com", user, emptyList(),false)
        val petsDAO = ArrayList(listOf(pantufas, bigodes))
    }

    @Test
    fun `basic test on getAll`(){
        Mockito.`when`(repo.findAllByRemovedFalse()).thenReturn(petsDAO)

        assertThat(pets.getAllPets(), equalTo(petsDAO as List<PetDAO>))
    }

    @Test
    fun `basic test on getOne`() {
        Mockito.`when`(repo.findById(1L)).thenReturn(Optional.of(pantufas));

        assertThat(pets.getPetById(1L), equalTo(pantufas))
    }

    @Test(expected = NotFoundException::class)
    fun `test on getOne() exception`() {
        //did not find the desired pet on the DB hence an empty Optional
        Mockito.`when`(repo.findById(anyLong())).thenReturn(Optional.empty())

        pets.getPetById(0L)
    }
/*
    @Test
    fun `test on adding a new pet`() {
        Mockito.`when`(repo.save(Mockito.any(PetDAO::class.java)))
                .then {
                    val pet:PetDAO = it.getArgument(0)
                    assertThat(pet.id, equalTo(0L))
                    assertThat(pet.name, equalTo(pantufas.name))
                    assertThat(pet.species, equalTo(pantufas.species))
                    assertThat(pet.appointments, equalTo(pantufas.appointments))
                    pet
                }

        pets.addNew(PetDAO(0L, "pantufas", "Dog", "www.google.com", user, emptyList(),false).apply { appointments = pantufas.appointments })
    }

    @Test(expected = PreconditionFailedException::class)
    fun `test on adding a new pet (Error)`() {
        val pantufasDTO = PetDTO(pantufas.id, pantufas.name, pantufas.species, pantufas.photo, pantufas.owner.id, pantufas.chip, false)
        pets.addNew(pantufasDTO) // pantufas has a non-0 id
    }

    @Test
    fun `test on retrieving appointments 1`() {
        val consulta1 = AppointmentDAO(1, LocalDateTime.now(),"consulta", AppointmentStatus.ACCEPTED,"", pantufas, pantufas.owner, vet)
        val consulta2 = AppointmentDAO(2, LocalDateTime.now(),"consulta", AppointmentStatus.ACCEPTED,"", pantufas, pantufas.owner, vet)
        pantufas.appointments = listOf(consulta1, consulta2)

        Mockito.`when`(repo.findByIdWithAppointment(pantufas.id)).thenReturn(Optional.of(pantufas))

        assertThat(pets.getAppointments(pantufas.id), equalTo(pantufas.appointments))
    }

    @Test
    fun `test on retrieving appointments 2`() {
        pantufas.appointments = emptyList()

        Mockito.`when`(repo.findByIdWithAppointment(pantufas.id)).thenReturn(Optional.of(pantufas))

        assertThat(pets.getAppointments(pantufas.id), equalTo(pantufas.appointments))
    }

    @Test
    fun `test on adding a new Appointment`() {
        val consulta = AppointmentDAO(0, Date(), "consulta", pantufas)

        pantufas.appointments = emptyList()

        Mockito.`when`(aptRepository.save(Mockito.any(AppointmentDAO::class.java)))
                .then {
                    val apt:AppointmentDAO = it.getArgument(0)
                    assertThat(apt.id, equalTo(0L))
                    assertThat(apt.desc, equalTo(consulta.desc))
                    assertThat(apt.date, equalTo(consulta.date))
                    assertThat(apt.pet, equalTo(pantufas))
                    apt
                }
        pets.newAppointment(consulta)
    }

    @Test(expected = PreconditionFailedException::class)
    fun `test on adding a new Appointment (Precondition Failed)`() {
        val consulta = AppointmentDAO(1, Date(), "consulta", pantufas)
        pets.newAppointment(consulta)
    }*/
}