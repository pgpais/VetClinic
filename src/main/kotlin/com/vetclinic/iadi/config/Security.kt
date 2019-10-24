package com.vetclinic.iadi.config

import com.vetclinic.iadi.services.ClientService
import com.vetclinic.iadi.services.RegisteredUserService
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class Security(val customcClientnfo:CustomClientInfoService,
                       val clients: ClientService) : WebSecurityConfigurerAdapter()
{
    override fun configure(http: HttpSecurity) {
        http.csrf().disable() // for now, we can disable cross site request forgery protection
                .authorizeRequests()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST,"/signup").permitAll()
                .antMatchers("/vet").hasRole("VET")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(UserPasswordAuthenticationFilterToJWT ("/login", super.authenticationManagerBean()),
                        BasicAuthenticationFilter::class.java)
                .addFilterBefore(UserPasswordSignUpFilterToJWT ("/signup", clients),
                        BasicAuthenticationFilter::class.java)
                .addFilterBefore(JWTAuthenticationFilter(),
                        BasicAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(BCryptPasswordEncoder().encode("password"))
                .authorities(emptyList())
                .and()
                .passwordEncoder(BCryptPasswordEncoder())
                .and()
                .userDetailsService(customcClientnfo)
                .passwordEncoder(BCryptPasswordEncoder())
    }



}