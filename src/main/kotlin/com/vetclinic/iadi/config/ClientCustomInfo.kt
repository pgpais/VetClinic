package com.vetclinic.iadi.config

import com.vetclinic.iadi.services.AdminService
import com.vetclinic.iadi.services.ClientService
import com.vetclinic.iadi.services.VetService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

class ClientCustomInfo(
        private val username:String,
        private val password:String,
        private val someAuthorities:MutableCollection<out GrantedAuthority>) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = someAuthorities

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = username

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}

@Service
class CustomClientInfoService(
        val clientService: ClientService,
        val adminService:AdminService,
        val vetService: VetService
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        username?.let {
            val clientDAO = clientService.getClientByUsername(it)
            val adminDAO = adminService.getAdminByUsername(it)
            val veterinarianDAO = vetService.getVetByUsername(it)
                        

            if( clientDAO.isPresent) {
                return ClientCustomInfo(clientDAO.get().username, clientDAO.get().pass, mutableListOf(SimpleGrantedAuthority("ROLE_CLIENT")))

            } else if (adminDAO.isPresent) {
               return  ClientCustomInfo(adminDAO.get().username, adminDAO.get().pass,
                        mutableListOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_VETERINARIAN")))

            } else if (veterinarianDAO.isPresent){
                return ClientCustomInfo(veterinarianDAO.get().username, veterinarianDAO.get().pass,
                        mutableListOf(SimpleGrantedAuthority("ROLE_VETERINARIAN")))
            } else
                throw UsernameNotFoundException(username)
            }

        throw UsernameNotFoundException(username)
    }
}
