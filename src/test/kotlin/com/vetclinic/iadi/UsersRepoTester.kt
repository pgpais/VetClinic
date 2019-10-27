package com.vetclinic.iadi

import com.vetclinic.iadi.model.*
import org.h2.engine.User
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class UsersRepoTester {

    @Autowired
    lateinit var admins:AdminRepository

    @Autowired
    lateinit var vets:VeterinaryRepository

    @Autowired
    lateinit var clients:ClientRepository

    @Autowired
    lateinit var users: UserRepository

    companion object {
        var admin: AdminDAO = AdminDAO(1L, "Admin123", "123","José","","",5,"")
        var vet: VeterinarianDAO = VeterinarianDAO(2L, "Vet123", "123","Maria", "","",6,"" ,emptyList(), emptyList())
        var client: ClientDAO = ClientDAO(3L,"Client123", "123", "Luis","","",5,"",emptyList(), emptyList())
    }

    /*@Test
    fun `delete all`(){
        admins.deleteAll()
        vets.deleteAll()
        clients.deleteAll()
    }*/

    @Test
    fun `find all admins`(){
        admins.deleteAll()
        assertThat(admins.findAll().toList(), equalTo(emptyList()))
    }

    @Test
    fun `Add and delete admin`(){
        val savedAdmin = admins.save(admin)
        assertThat(savedAdmin.id, not(equalTo(admin.id)))
        admins.deleteById(savedAdmin.id)
    }
    
}