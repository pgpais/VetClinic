package com.vetclinic.iadi.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.vetclinic.iadi.api.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import com.vetclinic.iadi.model.*
import com.vetclinic.iadi.services.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTester {

    @Autowired
    lateinit var mvc:MockMvc

    @MockBean
    lateinit var admins:AdminService

    companion object {

        val mapper = ObjectMapper().registerModule(KotlinModule())

        val admin = AdminDTO(0L, "AdminExtra", "123")

        val adminsURL = "/admin"
    }

    @Test
    fun `Test GET ONE admin`(){
        val adminDAO = AdminDAO(admin)

        Mockito.`when`(admins.getUserById(2)).thenReturn(adminDAO)

        val result = mvc.perform(get("$adminsURL/user/2"))
                .andExpect(status().isOk)
                .andReturn()

        val responseString = result.response.contentAsString
        val responseDTO = mapper.readValue<AdminDTO>(responseString)
        assertThat(responseDTO, equalTo(admin))
    }
}