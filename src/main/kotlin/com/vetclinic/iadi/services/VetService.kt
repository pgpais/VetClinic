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

        appointments.updateStatusById(id, reason, false)

    }

    fun acceptAppointment(id:Long){

        appointments.updateStatusById(id, "", true)

    }

    fun getPendingAppointments(id: Long): List<AppointmentDAO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

