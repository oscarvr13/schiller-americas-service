package com.shiller.americas.service;

import java.util.List;
import java.util.stream.Collectors;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shiller.americas.controller.client.CourseClient;
import com.shiller.americas.controller.client.CourseService;
import com.shiller.americas.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.shiller.americas.dto.PersonDto;
import com.shiller.americas.entity.Person;
import com.shiller.americas.repository.PersonRepository;
import com.google.common.collect.Lists;

@RequiredArgsConstructor
@Service
public class PersonService {

  private final PersonRepository personRepository;

  private final CourseService courseService;

  public List<PersonDto> getAllPersons() {
    return Lists.newArrayList(personRepository.findAll()).stream()
        .map(PersonDto.getBuilder()::build).collect(Collectors.toList());
  }

  public PersonDto getPerson(int personId) {
    return personRepository.findById(personId).map(PersonDto.getBuilder()::build).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found Person by Id"));
  }

  public PersonDto createPerson(PersonDto personToCreate) {
    Person person = Person.getBuilder().build(personToCreate);
    Person personSaved = personRepository.save(person);
    return PersonDto.getBuilder().build(personSaved);
  }

  public PersonDto updatePerson(int personId, PersonDto personToUpdate) {
    System.out.println("PersonDtoToUpdate: "+personToUpdate);
    return personRepository.findById(personId).map(person -> 
    {
      Person newPersonToSave =
          Person.getBuilder().withId(personId).withName(personToUpdate.getName())
              .withAge(personToUpdate.getAge()).withGender(personToUpdate.getGender())
              .withCode(person.getCode()).withCreatedAt(person.getCreatedAt()).build();
      return PersonDto.getBuilder().build(personRepository.save(newPersonToSave));
    })
        .orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found Person by Id"));
  }

  public boolean deletePerson(int personId) {
    boolean existsPerson = personRepository.existsById(personId);
    if (existsPerson) {
      personRepository.deleteById(personId);
      return true;
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found Person by Id");
    }
  }

  public ResponseEntity<List<CourseDto>> getAllCourses() {
    return courseService.getAllCourses();
  }
}
