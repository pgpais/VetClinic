package com.vetclinic.iadi

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
import com.vetclinic.iadi.services.ClientService
import com.vetclinic.iadi.services.NotFoundException
import com.vetclinic.iadi.services.PetService
import com.vetclinic.iadi.services.PreconditionFailedException
import java.util.*
import kotlin.collections.ArrayList


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTester {

    @Autowired
    lateinit var mvc:MockMvc

    @MockBean
    lateinit var clients:ClientService

    companion object {

        val mapper = ObjectMapper().registerModule(KotlinModule())

        val client = ClientDAO(1L, "Client", "123", emptyList(), emptyList())

        val usersURL = ""
        val clientsURL= "/client"
    }

    @Test
    fun `Test GET ONE user`(){
        Mockito.`when`(clients.getClientById(1)).thenReturn(client)

        val result = mvc.perform(get("$clientsURL/1"))
                .andExpect(status().isOk)
                .andReturn()
    }

    @Test
    fun `Test GET ONE user (Not Found)`(){
        Mockito.`when`(clients.getClientById(2)).thenThrow(NotFoundException("not found"))

        mvc.perform(get("$clientsURL/2"))
                .andExpect(status().is4xxClientError)
    }

}