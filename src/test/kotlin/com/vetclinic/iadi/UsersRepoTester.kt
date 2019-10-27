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
        var admin: AdminDAO = AdminDAO(-0L,"José", "123")
        var vet: VeterinarianDAO = VeterinarianDAO(0L, "Maria", "123", "", emptyList(), emptyList())
        var client: ClientDAO = ClientDAO(0L, "Luis", "123", emptyList(), emptyList())
    }

    @Test
    fun `delete all`(){
        admins.deleteAll()
        vets.deleteAll()
        clients.deleteAll()
    }

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