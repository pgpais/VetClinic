package com.vetclinic.iadi.config

import com.vetclinic.iadi.model.AdminRepository
import com.vetclinic.iadi.model.ClientRepository
import com.vetclinic.iadi.model.UserRepository
import com.vetclinic.iadi.model.VeterinaryRepository
import com.vetclinic.iadi.services.AdminService
import com.vetclinic.iadi.services.ClientService
import com.vetclinic.iadi.services.NotFoundException
import com.vetclinic.iadi.services.VetService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Exception
import javax.persistence.DiscriminatorValue

class ClientCustomInfo(
        private val id:Long,
        private val username:String,
        private val password:String,
        private val someAuthorities:MutableCollection<out GrantedAuthority>) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = someAuthorities

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = username

    fun getId() = id

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}

@Transactional
@Service
class CustomClientInfoService(
        val users:UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        username?.let {

            val usersDAO = users.findByUsername(username).orElseThrow { NotFoundException("Didn't find that user with username $username") }

            val type = usersDAO.getDiscriminatorValue()

            print(type)

            when (type) {
                "com.vetclinic.iadi.model.ClientDAO" -> return ClientCustomInfo(usersDAO.id,usersDAO.username, usersDAO.pass, mutableListOf(SimpleGrantedAuthority("ROLE_CLIENT")))
                "com.vetclinic.iadi.model.VeterinarianDAO"  -> return ClientCustomInfo(usersDAO.id,usersDAO.username, usersDAO.pass, mutableListOf(SimpleGrantedAuthority("ROLE_VET")))
                "com.vetclinic.iadi.model.AdminDAO"  -> return ClientCustomInfo(usersDAO.id,usersDAO.username, usersDAO.pass, mutableListOf(SimpleGrantedAuthority("ROLE_ADMIN")))
                else -> { // Note the block
                    throw Exception("when is badly done")
                }

            }

        }
        throw UsernameNotFoundException(username)

    }
}

