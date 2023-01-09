package org.arun.springtest.repository;

import org.arun.springtest.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    //Define Custom query using JPQL with index parameter
    @Query("select e from Student e where e.firstName = ?1 and e.lastName = ?2")
    Student findByJPQL(String firstName , String lastName);


    //Define Custom query using JPQL with named parameter
    @Query("select e from Student e where e.firstName =:firstName and e.lastName =:lastName")
    Student findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    //Define Custom query using NativeQuery with index parameter
    @Query(value = "select * from students e where e.first_name =?1 and e.last_name = ?2" , nativeQuery = true )
    Student findByNativeSQL(String firstName , String lastName);

    //Define Custom query using NativeQuery with named parameter
    @Query(value = "select * from students e where e.first_name =:firstName and e.last_name =:lastName" , nativeQuery = true )
    Student findByNativeSQLNamed(@Param("firstName") String firstName , @Param("lastName") String lastName);
}
