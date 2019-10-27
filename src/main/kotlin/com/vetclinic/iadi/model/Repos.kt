package com.vetclinic.iadi.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.annotation.PostConstruct

interface PetRepository : JpaRepository<PetDAO, Long> {

    fun findAllByDeletedFalse():List<PetDAO>

    fun findByName(name:String): MutableIterable<PetDAO>

    @Query("select p from PetDAO p inner join fetch p.appointments where p.id = :id")
    fun findByIdWithAppointment(id:Long) : Optional<PetDAO>

    @Query("select p from PetDAO p where p.owner =:owner")
    fun findAllByOwner(owner:String)

    @Query("select p from PetDAO p where p.owner =:owner and p.id = :id")
    fun findPetByOwnerAndId(id:Long,owner:String)

    @Modifying
    @Transactional
    @Query("update PetDAO p set p.deleted= false where p.id =: id")
    fun updateDeleted(id: Long)



}

interface AppointmentRepository: JpaRepository<AppointmentDAO, Long>{

    @Modifying
    @Transactional
    @Query("update AppointmentDAO apt set apt.status = :status, apt.reason = :reason where apt.id = :id")
    fun updateStatusById(id: Long, reason:String, status:AppointmentStatus)

    @Query("select a from AppointmentDAO a where a.vet.id = :id and a.status = 0 ")
    fun getPendingByVetId(id:Long) : List<AppointmentDAO>

}


interface VeterinaryRepository: JpaRepository<VeterinarianDAO, Long>{

    @Query("select v from VeterinarianDAO v inner join fetch v.appointments where v.id = :id")
    fun findByIdWithAppointment(id:Long) : Optional<VeterinarianDAO>

    @Query("select v from VeterinarianDAO v inner join fetch v.appointments a where v.id = :id and a.desc = '' ")
    fun findByIdWithAppointmentPending(id:Long) : Optional<VeterinarianDAO>

    @Modifying
    @Transactional
    @Query("update VeterinarianDAO v set v.schedule = :plus  where v.id = :vetId")
    fun updateShifts(vetId: Long, plus: List<ShiftsDAO>)

}

interface ShiftsRepository: JpaRepository<ShiftsDAO, Long>{

}

interface UserRepository: JpaRepository<RegisteredUsersDAO, Long>{


}

interface AdminRepository: JpaRepository<AdminDAO, Long>{


}

interface ClientRepository : JpaRepository<ClientDAO, Long> {
    @Query("select c from ClientDAO c inner join fetch c.appointments where c.id = :id")
    fun findByIdWithAppointment(id:Long) : Optional<ClientDAO>

    @Query("select c from ClientDAO c inner join fetch c.pets where c.id = :id")
    fun findByIdWithPets(id: Long): Optional<ClientDAO>
}