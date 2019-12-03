package com.vetclinic.iadi.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.annotation.PostConstruct

interface PetRepository : JpaRepository<PetDAO, Long> {

    fun findAllByRemovedFalse():List<PetDAO>

    fun findByName(name:String): MutableIterable<PetDAO>

    fun findByIdAndRemovedIsFalse(id:Long) : Optional<PetDAO>

    @Query("select p from PetDAO p inner join fetch p.appointments where p.id = :id and p.removed = false")
    fun findByIdWithAppointment(id:Long) : Optional<PetDAO>

    @Query("select p from PetDAO p where p.owner =:owner")
    fun findAllByOwner(owner:String)

    @Query("select p from PetDAO p where p.owner =:owner and p.id = :id and p.removed = false")
    fun findPetByOwnerAndId(id:Long,owner:String)

    @Modifying
    @Transactional
    @Query("update PetDAO p set p.removed=true where p.id =:id")
    fun removeById(id: Long)

    @Modifying
    @Transactional
    @Query("update PetDAO p set p.removed=true where p.id =:id and p.owner =:owner")
    fun removeByIdAndUserId(owner: ClientDAO, id: Long)



}

interface AppointmentRepository: JpaRepository<AppointmentDAO, Long>{

    @Modifying
    @Transactional
    @Query("update AppointmentDAO apt set apt.status = :status, apt.reason = :reason where apt.id = :id")
    fun updateStatusById(id: Long, reason:String, status:AppointmentStatus)

    @Query("select a from AppointmentDAO a where a.vet.id = :id and a.status = 0 ")
    fun getPendingByVetId(id:Long) : List<AppointmentDAO>

    fun getById(id:Long)
}


interface VeterinaryRepository: JpaRepository<VeterinarianDAO, Long> {


    fun findByUsername(username:String) : Optional<VeterinarianDAO>


    //TODO Is this right? 1=Accepted
    @Query("select v from VeterinarianDAO v inner join v.appointments a where v.username = :username and v.frozen = false and a.status = 1")
    fun findByUsernameWithAppointmentAccepted(username: String): Optional<VeterinarianDAO>

    @Query("select v from VeterinarianDAO v inner join v.appointments a where v.id = :id and v.frozen = false")
    fun findByIdWithAppointment(id: Long): Optional<VeterinarianDAO>

    @Query("select v from VeterinarianDAO v inner join fetch v.appointments a where v.id = :id and a.desc = ''  and v.frozen = false")
    fun findByIdWithAppointmentPending(id: Long): Optional<VeterinarianDAO>


    @Modifying
    @Transactional
    @Query("update VeterinarianDAO v set v.schedule = :plus  where v.id = :vetId")
    fun updateShifts(vetId: Long, plus: List<ShiftsDAO>)

    @Modifying
    @Transactional
    @Query("update VeterinarianDAO v set v.frozen = true where v.id =:vetId")
    override fun deleteById(vetId: Long)

    @Modifying
    @Transactional
    @Query("update VeterinarianDAO v set v.frozen = true")
    override fun deleteAll()

    fun findByIdAndFrozenIsFalse(id: Long): Optional<VeterinarianDAO>

    fun findByUsernameAndFrozenIsFalse(username: String): Optional<VeterinarianDAO>

    fun findAllByFrozenIsFalse(): List<VeterinarianDAO>

}

interface ShiftsRepository: JpaRepository<ShiftsDAO, Long>{

    fun findByVetId(id:Long) : List<ShiftsDAO>

}

interface UserRepository: JpaRepository<RegisteredUsersDAO, Long>{


     fun findByUsername(username:String) : Optional<RegisteredUsersDAO>

 /*
     @Modifying
     @Transactional
     @Query("update RegisteredUsersDAO u set u.id =:id, u.name =:name, u.username =:username, u.pass =:pass where u.id =:id")
     fun updateUser(id:Long, name: String, username: String, pass:String)
 */
}

interface AdminRepository: JpaRepository<AdminDAO, Long>{

    fun findByUsername(username:String) : Optional<AdminDAO>

}

interface ClientRepository : JpaRepository<ClientDAO, Long> {
    @Query("select c from ClientDAO c inner join fetch c.appointments where c.id = :id")
    fun findByIdWithAppointment(id:Long) : Optional<ClientDAO>

    @Query("select c from ClientDAO c inner join fetch c.pets where c.id = :id")
    fun findByIdWithPets(id: Long): Optional<ClientDAO>

    @Query("select c from ClientDAO c inner join fetch c.pets where c.username = :username")
    fun findByUsernameWithPets(username: String): Optional<ClientDAO>

    fun findByUsername(username:String) : Optional<ClientDAO>

}