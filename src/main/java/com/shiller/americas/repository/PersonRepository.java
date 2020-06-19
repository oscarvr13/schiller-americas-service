package com.shiller.americas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shiller.americas.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Integer>{

}
