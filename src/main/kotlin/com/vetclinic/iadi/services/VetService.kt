package com.vetclinic.iadi.services

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.AppointmentRepository
import com.vetclinic.iadi.model.VeterinaryRepository
import org.springframework.stereotype.Service

@Service
class VetService(val vets: VeterinaryRepository, val appointments: AppointmentRepository) {

    fun getVetbyId(id:Long) = vets.findById(id).orElseThrow{NotFoundException("There is no Veterinarian with Id $id")}

    fun getAppointments(id:Long): List<AppointmentDAO> {

        val vet  = vets.findByIdWithAppointment(id).orElseThrow { NotFoundException("There is no Vet with Id $id") }
        return vet.appointments
    }
    fun rejectAppointment(id:Long, reason:String){

        val apt = appointments.findById(id).orElseThrow { NotFoundException("There is no Vet with Id $id") }
        apt.status = false
        apt.reason = reason
        apt.update(apt)
    }

    }

