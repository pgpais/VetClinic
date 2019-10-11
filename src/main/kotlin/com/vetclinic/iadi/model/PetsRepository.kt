package com.vetclinic.iadi.model

import org.springframework.data.repository.CrudRepository

interface PetsRepository:CrudRepository<PetDAO, Long>