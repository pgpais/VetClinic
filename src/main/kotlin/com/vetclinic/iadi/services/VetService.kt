package com.vetclinic.iadi.services

import com.vetclinic.iadi.model.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class VetService(val vets: VeterinaryRepository, val appointments: AppointmentRepository) {

    fun getVetbyId(id:Long) = vets.findById(id).orElseThrow{NotFoundException("There is no Veterinarian with Id $id")}

    fun getAppointments(id:Long): List<AppointmentDAO> {

        val vet  = vets.findByIdWithAppointment(id).orElseThrow { NotFoundException("There is no Vet with Id $id") }
        return vet.appointments
    }
    fun rejectAppointment(id:Long, reason:String){
        val app = appointments.findById(id).orElseThrow(){NotFoundException("There is no Appointment with Id $id")}
        if(app.status == AppointmentStatus.PENDING)
        appointments.updateStatusById(id, reason, AppointmentStatus.REJECTED)
            else
        throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")
    }

    fun acceptAppointment(id:Long){
        val app = appointments.findById(id).orElseThrow(){NotFoundException("There is no Appointment with Id $id")}
        if(app.status == AppointmentStatus.PENDING)
        appointments.updateStatusById(id, "", AppointmentStatus.ACCEPTED)
        else
        throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")

    }

    fun completeAppointment(id:Long){
        val app = appointments.findById(id).orElseThrow(){NotFoundException("There is no Appointment with Id $id")}
        if(app.status == AppointmentStatus.ACCEPTED)
            appointments.updateStatusById(id, "", AppointmentStatus.COMPLETED)
        else
            throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")

    }

    fun getPendingAppointments(id: Long): List<AppointmentDAO> {
        val vet  = vets.findById(id).orElseThrow { NotFoundException("There is no Vet with Id $id") }
        return appointments.getPendingByVetId(id);
    }

    fun setSchedule(vetId: Long, adminId: Long, shifts: List<ShiftsDAO>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getVetByUsername(username:String)  :Optional<VeterinarianDAO> = vets.findByUsername(username)



}

