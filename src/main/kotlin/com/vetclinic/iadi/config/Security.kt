package com.vetclinic.iadi.config

import com.vetclinic.iadi.services.ClientService
import com.vetclinic.iadi.services.RegisteredUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
class Security(val customClientinfo:CustomClientInfoService,
                       val clients: ClientService) : WebSecurityConfigurerAdapter()
{
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/v2/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST,"/register").permitAll()
                .antMatchers("/home","/pets").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(UserPasswordAuthenticationFilterToJWT ("/login", super.authenticationManagerBean()),
                        BasicAuthenticationFilter::class.java)
                .addFilterBefore(ClientPasswordSignUpFilterToJWT ("/register", clients),
                        BasicAuthenticationFilter::class.java)
                .addFilterBefore(JWTAuthenticationFilter(),
                        BasicAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customClientinfo).passwordEncoder(BCryptPasswordEncoder())
    }




}
