package com.vetclinic.iadi.config

import com.vetclinic.iadi.services.ClientService
import org.springframework.security.core.GrantedAuthority
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
        val clientService: ClientService
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        username?.let {
            val clientDAO = clientService.getClientByName(it)
            if( clientDAO.isPresent) {
                return ClientCustomInfo(clientDAO.get().username, clientDAO.get().pass, mutableListOf())
            } else
                throw UsernameNotFoundException(username)
        }
        throw UsernameNotFoundException(username)
    }
}
