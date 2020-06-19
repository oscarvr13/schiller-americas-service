package com.shiller.americas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;
import com.shiller.americas.dto.PersonDto;
import com.shiller.americas.entity.Person;
import com.shiller.americas.repository.PersonRepository;
import static org.mockito.ArgumentMatchers.any;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private PersonService personService;
  
  @Rule
  public ExpectedException exceptionRule = ExpectedException.none();

  /**
   * Test to obtain all the persons.
   * Must evaluate the business logic.
   */
  @Test
  public void testGetAllPersons() {
    List<Person> listPersons = Arrays.asList(
        Person.getBuilder().withId(1)
        .withName("Luis").withAge(34).withGender("M")
        .withCode("ABCDEFGHIJ").withCreatedAt(new Date())
        .build(),
        Person.getBuilder().withId(2)
        .withName("Fernanda").withAge(24).withGender("F")
        .withCode("MNJOPQRSTU").withCreatedAt(new Date())
        .build()
        );
    when(personRepository.findAll())
    .thenReturn(listPersons);
    
    List<PersonDto> listPersonDto = personService.getAllPersons();
    assertThat(listPersonDto).extracting("personId")
    .containsExactly(1, 2);
     assertThat(listPersonDto).extracting("name")
    .containsExactly("Luis", "Fernanda");
  }
  
  /**
   * Test to obtain one person by id.
   * 
   *Must evaluate the business logic.
   */
  @Test
  public void testGetPerson() {
    Person person = Person.getBuilder().withId(1)
        .withName("Luis").withAge(34).withGender("M")
        .withCode("ABCDEFGHIJ").withCreatedAt(new Date())
        .build();
    when(personRepository.findById(1))
    .thenReturn(Optional.of(person));
    
    PersonDto personDto = personService.getPerson(1);
    assertThat(personDto).extracting("personId", "name", "age")
    .containsExactly(1,"Luis", 34);
  }
  
  /**
   * Test obtain not existing personel
   */
  @Test
  public void testGetPersonNoExistPerson() {
      exceptionRule.expect(ResponseStatusException.class);
      exceptionRule.expectMessage("Not Found Person by Id");
      when(personRepository.findById(4))
              .thenReturn(Optional.empty());
      PersonDto person = personService.getPerson(4);
  }
  
  /**
   * Test to create a person
   * 
   */
  @Test
  public void testCreatePerson() {
    PersonDto personToSave = new PersonDto("Fernando", 24, "M");
    Person person = Person.getBuilder().withId(null).withName("Fernando")
        .withAge(24).withGender("M").withCode(personToSave.getCode())
        .withCreatedAt(personToSave.getCreateDate()).build();
    when(personRepository.save(any(Person.class)))
    .thenReturn(
        Person.getBuilder().withId(1).withName(person.getName())
        .withAge(person.getAge()).withGender(person.getGender())
        .withCode(person.getCode()).withCreatedAt(person.getCreatedAt())
        .build()
        );
    PersonDto personSaved = personService.createPerson(personToSave);
    assertThat(personSaved).extracting("personId", "name", "age")
    .containsExactlyInAnyOrder(1, "Fernando", 24);
    assertThat(personSaved).extracting("code", "createDate")
    .isNotEmpty().isNotNull();
}
  
  /**
   * Test to update a person.
   * Verify the business logic when creating.
   */
  @Test
  public void testUpdatePerson() {
    PersonDto personDtoUpdate = new PersonDto("Fernando", 24, "M");
    Person personToUpdate = Person.getBuilder().withId(1).withName("Fernando")
        .withAge(24).withGender("M").withCode(personDtoUpdate.getCode())
        .withCreatedAt(personDtoUpdate.getCreateDate()).build();
    when(personRepository.findById(1)).thenReturn(
        Optional.of(personToUpdate)
        );
    when(personRepository.save(any(Person.class)))
    .thenReturn(personToUpdate);
    PersonDto personUpdated = personService.updatePerson(1, personDtoUpdate);
    assertThat(personUpdated).extracting("personId", "name", "age")
    .containsExactlyInAnyOrder(1, "Fernando", 24);
    assertThat(personUpdated).extracting("code", "createDate")
    .isNotEmpty().isNotNull();
    
}
  
  /**
   * Test to delete a person.
   */
  @Test
  public void testDeletePerson() {
    int personId = 1;
    when(personRepository.existsById(personId))
            .thenReturn(true);
    doNothing().when(personRepository).deleteById(personId);
    boolean deletedPerson = personService.deletePerson(personId);
    assertThat(deletedPerson).isTrue();
  }
  
  @Test
  public void testDeletePersonNoExistPerson() {
    exceptionRule.expect(ResponseStatusException.class);
    exceptionRule.expectMessage("Not Found Person by Id");
     int personId = 4;
      when(personRepository.existsById(personId))
              .thenReturn(false);
   boolean deletedPerson = personService.deletePerson(personId);

  }
  
  
  
}
