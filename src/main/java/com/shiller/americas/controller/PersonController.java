package com.shiller.americas.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.shiller.americas.controller.api.PersonApi;
import com.shiller.americas.dto.PersonDto;
import com.shiller.americas.service.PersonService;
import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
@RestController
public class PersonController implements PersonApi {

  private  final PersonService personService;
  
  public PersonController(PersonService personService) {
    this.personService = personService;
  }
  
  /**
   * Get all the persons.
   * @return A ResponseEntity object with the List of PersonDto as body
   */
  @Override
  public ResponseEntity<List<PersonDto>> getPersons() {
    return ResponseEntity.ok(personService.getAllPersons());
  }
  
  /**
   * Get a person.
   * @param personId the person Id we are looking for
   * @return A ResponseEntity object with the List of PersonDto as body
   */
  @Override
  public ResponseEntity<PersonDto> getPerson(int personId) {
    System.out.println("Entral getPerson" + personId);
    return ResponseEntity.ok(personService.getPerson(personId));
  }
  
  /**
   * Create a person.
   * @param personToCreate the body of the person to create
   * @return A ResponseEntity object with the created PersonDto as body
   */
  @Override
  public ResponseEntity<PersonDto> createPerson(PersonDto personToCreate) {
    PersonDto personDto = personService.createPerson(personToCreate);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{personId}").buildAndExpand(personDto.getPersonId()).toUri();
    return ResponseEntity.created(location).body(personDto);
  }
  
  /**
   * Update a person.
   * @param personToUpdate the body of the person to update.
   * @return A ResponseEntity object with the updated PersonDto as body
   */
  @Override
  public ResponseEntity<PersonDto> updatePerson(int personId, PersonDto personToUpdate) {
    System.out.println("Entra al update Person");
    PersonDto personDto = personService.updatePerson(personId, personToUpdate);
    return ResponseEntity.ok(personDto);
  }

  /**
   * Delete a person.
   * @param personId the person Id to delete.
   * @return A ResponseEntity object with no content.
   */
  @Override
  public ResponseEntity deletePerson(int personId) {
    System.out.println("Entra al delete Person");
    boolean isDeleted = personService.deletePerson(personId); 
    return ResponseEntity.noContent().build();
  }
}
