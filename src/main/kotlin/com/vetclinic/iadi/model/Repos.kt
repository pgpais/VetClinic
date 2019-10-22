package com.vetclinic.iadi.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface PetRepository : JpaRepository<PetDAO, Long> {

    fun findByName(name:String): MutableIterable<PetDAO>

    @Query("select p from PetDAO p inner join fetch p.appointments where p.id = :id")
    fun findByIdWithAppointment(id:Long) : Optional<PetDAO>

    @Query("select p from PetDAO p where p.owner =:owner")
    fun findAllByOwner(owner:String)

    @Query("select p from PetDAO p where p.owner =:owner and p.id = :id")
    fun findPetByOwnerAndId(id:Long,owner:String)

}

interface AppointmentRepository: JpaRepository<AppointmentDAO, Long>{

    fun findAllByIdAndStatus(id:Long, status: String) : List<AppointmentDAO>

    fun findAllByVet_Id(id: Long) : List<AppointmentDAO>

    @Modifying
    @Transactional
    @Query("update AppointmentDAO apt set apt.status = :status, apt.reason = :reason where apt.id = :id")
    fun updateStatusById(id: Long, reason:String, status:String)
}


interface VeterinaryRepository: JpaRepository<VeterinarianDAO, Long>{



}
interface ShiftsRepository: JpaRepository<ShiftsDAO, Long>{

}


interface AdminRepository: JpaRepository<AdminDAO, Long>{



}

interface ClientRepository : JpaRepository<ClientDAO, Long> {


}