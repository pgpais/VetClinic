package com.vetclinic.iadi

import com.vetclinic.iadi.api.PetDTO
import com.vetclinic.iadi.model.PetDAO
import com.vetclinic.iadi.services.PetService
import org.hamcrest.Matchers.equalTo
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    lateinit var mvc:MockMvc

    @MockBean
    lateinit var pets:PetService

    //static info
    companion object{
        val manel = PetDAO(1L, "manel", "dog")
        val cat = PetDAO(2L, "cat", "cat")

        val petsDAO = mutableListOf<PetDAO>(manel, cat)
        val expectedDTO = petsDAO.map { PetDTO(it.id, it.name, it.species) }
    }


    //test service and controller
    //$ means get anything
    @Test
    fun `test AllPets`(){

        //when someone calls getallpets() return the list with manel and cat
        Mockito.`when`(pets.getAllPets()).thenReturn(petsDAO)

        val result =
        mvc.perform(get("/pets"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$",hasSize<Any>(petsDAO.size)))
                .andReturn()

        /*
        val resulString = result.response.contentAsString
        val petsDTO = .readValue<List><PetDTO>>(resulString)

        assertThat(petsDTO, equalTo(expectedDTO))

         */
    }

    /*@Test
    fun `add Pet`(){

        val birb = PetDTO(0, "louro", "bird")

        val birbDAO = PetDAO(birb.id, birb.name, birb.species)

        //when add pet is called with any petDAO
        Mockito.`when`(pets.addPet(Mockito.any(PetDAO::class.java))).then { inv -> assertThat(inv.getArgument(0), equalTo(birbDAO)) }


        val json = mapper.writeValueAsString(manel)
        mvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk)
    }
*/
}