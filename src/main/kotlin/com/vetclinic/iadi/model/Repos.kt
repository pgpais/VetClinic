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

    @Modifying
    @Transactional
    @Query("update AppointmentDAO apt set apt.status = :status, apt.reason = :reason where apt.id = :id")
    fun updateStatusById(id: Long, reason:String, status:AppointmentStatus)

    @Query("select a from AppointmentDAO a where a.vet.vetId = :id and a.status = 0 ")
    fun getPendingByVetId(id:Long) : List<AppointmentDAO>

    @Query("select a from AppointmentDAO a where a.id = :id")
    fun getById(id:Long) : Optional <AppointmentDAO>
}


interface VeterinaryRepository: JpaRepository<VeterinarianDAO, Long>{

    @Query("select v from VeterinarianDAO v inner join fetch v.appointments where v.vetId = :id")
    fun findByIdWithAppointment(id:Long) : Optional<VeterinarianDAO>

    @Query("select v from VeterinarianDAO v inner join fetch v.appointments a where v.vetId = :id and a.desc = '' ")
    fun findByIdWithAppointmentPending(id:Long) : Optional<VeterinarianDAO>

}
interface ShiftsRepository: JpaRepository<ShiftsDAO, Long>{

}


interface AdminRepository: JpaRepository<AdminDAO, Long>{



}

interface ClientRepository : JpaRepository<ClientDAO, Long> {
    @Query("select c from ClientDAO c inner join fetch c.appointments where c.id = :id")
    fun findByIdWithAppointment(id:Long) : Optional<ClientDAO>

    @Query("select c from ClientDAO c inner join fetch c.pets where c.id = :id")
    fun findByIdWithPets(id: Long): Optional<ClientDAO>


}