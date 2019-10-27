package com.vetclinic.iadi.services

import com.vetclinic.iadi.model.*
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class VetService(val vets: VeterinaryRepository, val appointments: AppointmentRepository, val shiftRep: ShiftsRepository) {

    fun getVetbyId(id:Long) = vets.findByIdAndFrozenIsFalse(id).orElseThrow{NotFoundException("There is no Veterinarian with Id $id")}

    fun getAllVets():Iterable<VeterinarianDAO> = vets.findAll()

    fun getAppointments(id:Long): List<AppointmentDAO> {

        val vet  = vets.findByIdWithAppointment(id).orElseThrow { NotFoundException("There is no Vet with Id $id") }
        return vet.appointments
    }
    fun rejectAppointment(id:Long, reason:String){
        val app = appointments.findById(id).orElseThrow{NotFoundException("There is no Appointment with Id $id")}

        if(app.status == AppointmentStatus.PENDING)
            appointments.updateStatusById(id, reason, AppointmentStatus.REJECTED)
        else
            throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")
    }

    fun acceptAppointment(id:Long){
        val app = appointments.findById(id).orElseThrow{NotFoundException("There is no Appointment with Id $id")}

        if(app.status == AppointmentStatus.PENDING)
            appointments.updateStatusById(id, "", AppointmentStatus.ACCEPTED)
        else
            throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")

    }

    fun completeAppointment(id:Long){
        val app = appointments.findById(id).orElseThrow{NotFoundException("There is no Appointment with Id $id")}
        if(app.status == AppointmentStatus.ACCEPTED)
            appointments.updateStatusById(id, "", AppointmentStatus.COMPLETED)
        else
            throw PreconditionFailedException("The Appointment cannot be completed because it's Status is ${app.status}")

    }

    fun getPendingAppointments(id: Long): List<AppointmentDAO> {
        vets.findByIdAndFrozenIsFalse(id).orElseThrow { NotFoundException("There is no Vet with Id $id") }
        return appointments.getPendingByVetId(id);
    }

    fun addShift(vetId: Long, newShift:ShiftsDAO) {

        val shifts = getVetbyId(vetId).schedule

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

    fun getSchedule(id: Long): List<ShiftsDAO> = emptyList()
}



