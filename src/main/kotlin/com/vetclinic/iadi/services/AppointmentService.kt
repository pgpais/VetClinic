package com.vetclinic.iadi.services

import com.vetclinic.iadi.model.AppointmentDAO
import com.vetclinic.iadi.model.AppointmentRepository
import org.springframework.stereotype.Service

@Service
class AppointmentService(val appointments: AppointmentRepository) {

    fun getAllAppointments():Iterable<AppointmentDAO> = appointments.findAll()

    fun getAppointmentByID(id:Long) = appointments.findById(id).orElseThrow{NotFoundException("There is no Appointment with Id $id")}


    fun delete(id: Long) {

        //just to make sure it exists
        val apt = getAppointmentByID(id)
        appointments.delete(apt)
    }

    fun newAppointment(apt:AppointmentDAO) {

        if(apt.id != 0L){
            throw PreconditionFailedException("Id must be 0 on insertion")
        }
        else{
            appointments.save(apt)
        }
    }


}