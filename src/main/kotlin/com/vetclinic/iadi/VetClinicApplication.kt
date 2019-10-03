package com.vetclinic.iadi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VetClinicApplication


fun main(args: Array<String>) {
    runApplication<VetClinicApplication>(*args)
}
