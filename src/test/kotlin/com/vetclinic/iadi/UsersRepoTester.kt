package com.vetclinic.iadi

import com.vetclinic.iadi.model.*
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
    lateinit var users: UserRepository

    companion object {
        var admin: AdminDAO = AdminDAO(1L,"José", "123")
        var vet: VeterinarianDAO = VeterinarianDAO(2L, "Maria", "123", "", emptyList(), emptyList())
        var client: ClientDAO = ClientDAO(3L, "Luis", "123", emptyList(), emptyList())
    }

    @Test
    fun `delete all`(){
        users.deleteAll()
    }

    @Test
    fun `find all admins`(){
        admins.deleteAll()
        assertThat(admins.findAll().toList(), equalTo(emptyList()))
    }
}