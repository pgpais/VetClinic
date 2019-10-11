package com.vetclinic.iadi.model

import org.springframework.data.repository.CrudRepository

interface ClientsRepository: CrudRepository<ClientDAO, String> {
}