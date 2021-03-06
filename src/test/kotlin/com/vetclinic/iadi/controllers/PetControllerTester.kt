package com.vetclinic.iadi.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.vetclinic.iadi.api.AppointmentDTO
import com.vetclinic.iadi.api.ClientDTO
import com.vetclinic.iadi.api.PetAptsDTO
import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.model.*
import com.vetclinic.iadi.services.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTester {

    @Autowired
    lateinit var mvc:MockMvc

    @MockBean
    lateinit var pets:PetService

    @MockBean
    lateinit var clientRepo: ClientRepository

    @MockBean
    lateinit var clients:ClientService

    @MockBean
    lateinit var vets:VetService



    companion object {
        // To avoid all annotations JsonProperties in data classes
        // see: https://github.com/FasterXML/jackson-module-kotlin
        // see: https://discuss.kotlinlang.org/t/data-class-and-jackson-annotation-conflict/397/6
        val mapper = ObjectMapper().registerModule(KotlinModule())

        val user = ClientDAO(4L,"Client123", "123", "manel","","",6,"",emptyList(), emptyList())

        val vet = VeterinarianDAO(5L,"VET123", "123","Joaquina" ,"www.google.com", "",7,"",emptyList(), emptyList())

        val pantufas = PetDAO(1L, "pantufas", "Dog", "www.google.com", user, emptyList(),false)
        val bigodes = PetDAO(2L, "bigodes", "Cat", "www.google.com", user, emptyList(),false)
        val petsDAO = ArrayList(listOf(pantufas, bigodes))

        val petsAptsDTO =
                petsDAO.map { PetAptsDTO(PetDTO(it),
                        it.appointments.map { AppointmentDTO(it) }) }

        val petsURL = "/pets"
        val usersURL= ""
    }

    @Test
    fun `Test GET all pets`() {
        Mockito.`when`(pets.getAllPets()).thenReturn(petsDAO)

        val result = mvc.perform(get(petsURL))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$", hasSize<Any>(petsAptsDTO.size)))
                .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<PetAptsDTO>>(responseString)
        assertThat(responseDTO, equalTo(petsAptsDTO))
    }

    @Test
    fun `Test Get One Pet`() {
        Mockito.`when`(pets.getPetById(1)).thenReturn(pantufas)

        val result = mvc.perform(get("$petsURL/1"))
                .andExpect(status().isOk)
                .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<PetAptsDTO>(responseString)
        assertThat(responseDTO, equalTo(petsAptsDTO[0]))
    }

    @Test
    fun `Test GET One Pet (Not Found)`() {
        Mockito.`when`(pets.getPetById(2)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$petsURL/2"))
                .andExpect(status().is4xxClientError)
    }

    fun <T>nonNullAny(t:Class<T>):T = Mockito.any(t)

    @Test
    fun `Test POST One Pet`() {
        val louroDAO = PetDAO(0L, "louro", "Papagaio", "www.google.com", user, emptyList(),false)
        val louro = PetDTO(louroDAO)

        val userDTO = ClientDTO(user.id, user.name, user.username, user.pass,user.photo,user.email,user.phone,user.address)

        val userJSON = mapper.writeValueAsString(userDTO)
        val louroJSON = mapper.writeValueAsString(louro)

        Mockito.`when`(clients.getClientById(nonNullAny(Long::class.java)))
                .thenReturn(user);

        Mockito.`when`(pets.addNew(nonNullAny(PetDTO::class.java), user.id))
                .then { assertThat(it.getArgument(0), equalTo(louroDAO)); it.getArgument(0) }

        mvc.perform(post(petsURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(louroJSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `Test checking appointments`() {
        val louro = PetDAO(0, "louro", "Papagaio","www.google.com", user, emptyList(),false)
        val apt = AppointmentDAO(2, LocalDateTime.now(),"consulta",AppointmentStatus.ACCEPTED,"", louro, user, vet)
        louro.appointments = listOf(apt)

        Mockito.`when`(pets.getAppointments(1)).thenReturn(listOf(apt))

        //val result =
        mvc.perform(get("$petsURL/1/appointments"))
                .andExpect(status().isOk)
                .andReturn()

        /*
        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<List<PetAptsDTO>>(responseString)
        assertThat(responseDTO, equalTo(petsAptsDTO))
        */
    }

    @Test
    fun `Test checking appointments of non pet`() {
        Mockito.`when`(pets.getAppointments(1))
                .thenThrow(NotFoundException("not found"))

        mvc.perform(get("$petsURL/1/appointments"))
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun `Test adding an appointment to a pet`() {
        val louro = PetDAO(1, "louro", "Papagaio","www.google.com", user, emptyList(),false)
        val apt = AppointmentDTO(0, LocalDateTime.now(), "consulta", AppointmentStatus.ACCEPTED, "", louro.id, user.id, vet.id)
        val aptDAO = AppointmentDAO(apt,louro, user, vet)
        louro.appointments = listOf(aptDAO)

        val aptJSON = mapper.writeValueAsString(apt)

        Mockito.`when`(pets.newAppointment(1L, nonNullAny(AppointmentDTO::class.java)))
                .then { assertThat( it.getArgument(0), equalTo(aptDAO)); it.getArgument(0) }

        Mockito.`when`(pets.getPetById(1)).thenReturn(louro)
        Mockito.`when`(vets.getVetbyId(vet.id)).thenReturn(vet)

        mvc.perform(post("$petsURL/appointments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aptJSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `Bad request on id not 0`() {
        val louro = PetDAO(1, "louro", "Papagaio","www.google.com", user, emptyList(),false)
        val apt = AppointmentDTO(2, LocalDateTime.now(), "consulta", AppointmentStatus.ACCEPTED, "", louro.id, user.id, vet.id)
        val aptDAO = AppointmentDAO(apt,louro, user, vet)
        louro.appointments = listOf(aptDAO)

        val aptJSON = mapper.writeValueAsString(apt)

        Mockito.`when`(pets.newAppointment(1L, nonNullAny(AppointmentDTO::class.java)))
                .thenThrow( PreconditionFailedException("id 0"))

        Mockito.`when`(pets.getPetById(1)).thenReturn(louro)

        mvc.perform(post("$petsURL/1/appointments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aptJSON))
                .andExpect(status().is4xxClientError)

    }
}