package com.vetclinic.iadi.services

import com.vetclinic.iadi.api.ShiftsDTO
import com.vetclinic.iadi.api.VeterinarianDTO
import com.vetclinic.iadi.model.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


@Service
class VetService(val vets: VeterinaryRepository, val appointments: AppointmentRepository, val shiftRep: ShiftsRepository) {

    fun createVet(vet: VeterinarianDTO) {
        val vetDAO = VeterinarianDAO(vet, emptyList(), emptyList())
        if( vetDAO.id != 0L){
            throw PreconditionFailedException("Id must be 0 on insertion")
        } else
            vets.save(vetDAO)
    }

    fun getVetbyId(id:Long) = vets.findByIdAndFrozenIsFalse(id).orElseThrow{NotFoundException("There is no Veterinarian with Id $id")}

    fun getVetbyUsername(username: String) = vets.findByUsernameAndFrozenIsFalse(username).orElseThrow{NotFoundException("There is no Veterinarian with username $username")}


    fun getAllVets():Iterable<VeterinarianDAO> = vets.findAll()

    fun getVetByUsername(username:String)  : Optional<VeterinarianDAO> = vets.findByUsername(username)


    fun getAcceptedAppointments(username: String): List<AppointmentDAO> {

        val vet  = vets.findByUsernameWithAppointmentAccepted(username).orElseThrow { NotFoundException("There is no Vet with Id $username") }
        return vet.appointments
    }

    fun getAppointments(username: String): List<AppointmentDAO> {
        val vet  = vets.findByUsernameWithAppointment(username).orElseThrow { NotFoundException("There is no Vet with Id $username") }
        return vet.appointments
    }
/*
    fun rejectAppointment(id:Long, reason:String){
        val app = appointments.findById(id).orElseThrow{NotFoundException("There is no Appointment with Id $id")}

        if(app.status == AppointmentStatus.PENDING)
            appointments.updateStatusById(id, reason, AppointmentStatus.REJECTED)
        else
            throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")
    }


 */
    /*
    fun acceptAppointment(id:Long){
        val app = appointments.findById(id).orElseThrow{NotFoundException("There is no Appointment with Id $id")}

        if(app.status == AppointmentStatus.PENDING) {

            if (checkIfAcceptable(app.vet, app)) {
                appointments.updateStatusById(id, "", AppointmentStatus.ACCEPTED)
            } else {
                appointments.updateStatusById(id, "Out of working hours", AppointmentStatus.REJECTED)
            }
        }

        else{
            throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")
        }

    }


     */
    fun checkIfAcceptable(vet:VeterinarianDAO, app:AppointmentDAO):Boolean {

        return true
        vet.schedule.forEach {

            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val dateTime = LocalDateTime.parse(app.date, formatter)
            //has to be in the middle of a shift and can't
            if (dateTime.isAfter(it.start) and dateTime.isBefore(it.end) and (ChronoUnit.MINUTES.between(dateTime, it.end) <= 30))
                return true

        }

        return false

    }
/*
    fun completeAppointment(id:Long){
        val app = appointments.findById(id).orElseThrow{NotFoundException("There is no Appointment with Id $id")}
        if(app.status == AppointmentStatus.ACCEPTED)
            appointments.updateStatusById(id, "", AppointmentStatus.COMPLETED)
        else
            throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")

    }
    */


    fun getPendingAppointments(username: String): List<AppointmentDAO> {
        val vet = vets.findByUsernameAndFrozenIsFalse(username).orElseThrow { NotFoundException("There is no Vet with Id $username") }
        return appointments.getPendingByVetId(vet.id);
    }

    fun setSchedule(username: String, shifts: List<ShiftsDTO>) {
        val vet = getVetbyUsername(username)
        vet.schedule = shifts.map { ShiftsDAO(it, vet)}
        vets.save(vet)
    }

    fun addShift(username: String, newShift:ShiftsDAO) { //TODO: why is this not used?

        val shifts = getVetbyUsername(username).schedule

        val duration = ChronoUnit.HOURS.between(newShift.start, newShift.end)

        //can't have shifts more than 12h
        if (duration > 12) {

            throw Exception("More than 12h")
            //fail
        }

        if (shifts.isEmpty()) {
            shiftRep.save(newShift)

        } else {
            var weekcounter = duration
            var monthcounter = duration

            shifts.forEach {

                //can't begin a shift in the middle of a shift and can't end a shift in the middle of a shift
                if (
                        (newShift.start.isBefore(it.end) and newShift.end.isAfter(it.end) or
                                (newShift.start.isBefore(it.start) and newShift.end.isBefore(it.end)))) {


                    throw Exception("New Shift " + newShift.start + " to " +newShift.end + " Middle of shift " + it.start +" to "+ it.end)



                }

                //can't have a shift that engulfs another shift
                else if (
                        (newShift.start.isBefore(it.start) and newShift.end.isAfter(it.end))
                ) {
                    throw Exception("New Shift " + newShift.start +" to " + newShift.end + " Engulfs shift " +  it.start+" to "  + it.end)
                    //fail
                }

                //can't work if he had a shift longer than 6h and the difference between his new shift start and old end is lass than 8h
                else if (
                        (
                                (ChronoUnit.HOURS.between(it.start, it.end) >= 6) and
                                        (ChronoUnit.HOURS.between(newShift.start, it.end) < 8)
                                )
                ) {
                    throw Exception("Vet has to rest after 6h shifts")

                }

                if (ChronoUnit.MONTHS.between(it.start, newShift.start) < 1) {
                    monthcounter += ChronoUnit.HOURS.between(it.start, it.end)

                }
                if (ChronoUnit.WEEKS.between(it.start, newShift.start) < 1) {
                    weekcounter += ChronoUnit.HOURS.between(it.start, it.end)

                }


            }
            if (monthcounter < 160) {
                throw Exception("Not enough hours for the month")
            }

            else if (weekcounter > 40) {
                throw Exception("Too many hours for the week")
            }
            else
                shiftRep.save(newShift)


        }
    }

    fun getSchedule(username: String): List<ShiftsDAO> {
        val vet = vets.findByUsername(username).orElseThrow { NotFoundException("There is no Appointment with Username $username") }
        return shiftRep.findByVetId(vet.id)
    }

    fun update(id: Long, vet: VeterinarianDTO) {
        val oldVetDAO = getVetbyId(id)
        val newVetDAO = VeterinarianDAO(id, oldVetDAO.username, vet, oldVetDAO.schedule, oldVetDAO.appointments)
        vets.save(newVetDAO)
    }

    fun updateAppointment(aptId: Long, mode: String, reason: String?) {

        val app = appointments.findById(aptId).orElseThrow{NotFoundException("There is no Appointment with Id $aptId")}

        if(app.status == AppointmentStatus.PENDING) {
            when (mode) {
                "rejected" -> {
                    reason?.let {
                        appointments.updateStatusById(aptId, reason, AppointmentStatus.REJECTED)
                    }
                    throw Exception("Must give reason when rejecting appointment")
                }
                "accepted" -> {

                    if (checkIfAcceptable(app.vet, app)) {
                        appointments.updateStatusById(aptId, "", AppointmentStatus.ACCEPTED)
                    } else {
                        appointments.updateStatusById(aptId, "Out of working hours", AppointmentStatus.REJECTED)
                    }
                }
                    }
            }
        else if(app.status == AppointmentStatus.ACCEPTED && mode == "completed")
            appointments.updateStatusById(aptId, "", AppointmentStatus.COMPLETED)

        else throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")

    }



}



