package org.example.restspringbootudemy.repositories;

import org.example.restspringbootudemy.entities.Person;
import org.example.restspringbootudemy.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Modifying
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id =:id")
    void disable(@Param("id") Long id);

    Page<Person> findByFirstNameIgnoreCase(String firstName, Pageable pageable);

}