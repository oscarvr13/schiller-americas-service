package com.shiller.americas;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.shiller.americas.dto.PersonDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PersonControllerTests {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testGetAllPersons() {
    ResponseEntity<PersonDto[]> response = this.restTemplate
        .getForEntity(URI.create("/shilleramericas/api/v1/persons"), PersonDto[].class);
    List<PersonDto> persons = Arrays.asList(response.getBody());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(persons).hasSize(3).extracting("personId").containsExactly(1, 2, 3);
    assertThat(persons).extracting("name").containsExactly("Juan", "Maria", "Luis");
  }

  @Test
  public void testGetPersonById() {
    ResponseEntity<PersonDto> response = this.restTemplate
        .getForEntity(URI.create("/shilleramericas/api/v1/persons/1"), PersonDto.class);
    PersonDto person = response.getBody();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(person).extracting("personId", "name", "age", "gender").containsExactly(1,
        "Juan", 24, "M");
  }

  @Test
  public void testCreatePerson() {
    PersonDto personToCreate = new PersonDto("Mario", 32);
    ResponseEntity<PersonDto> response = this.restTemplate.postForEntity(
        URI.create("/shilleramericas/api/v1/persons"), personToCreate, PersonDto.class);
    PersonDto personDtoInserted = response.getBody();
    String location = response.getHeaders().getLocation().toString();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(location)
        .contains("/shilleramericas/api/v1/persons/" + personDtoInserted.getPersonId());
    assertThat(personDtoInserted).extracting("name", "age", "gender", "code").containsExactly(
        personToCreate.getName(), personToCreate.getAge(), personToCreate.getGender(),
        personToCreate.getCode());

    response = this.restTemplate.getForEntity(URI.create(location), PersonDto.class);
    PersonDto personDto = response.getBody();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(personDto).extracting("personId", "name", "age", "gender", "code").containsExactly(
        personDtoInserted.getPersonId(), personDtoInserted.getName(), personDtoInserted.getAge(),
        personDtoInserted.getGender(), personDtoInserted.getCode());
  }

  @Test
  public void testUpdatePerson() {

    ResponseEntity<PersonDto> response = this.restTemplate
        .getForEntity(URI.create("/shilleramericas/api/v1/persons/1"), PersonDto.class);
    PersonDto personDto = response.getBody();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(personDto).extracting("personId").isEqualTo(1);

    PersonDto personToUpdate = new PersonDto("Mario", 24);
    HttpEntity<PersonDto> request = new HttpEntity<>(personToUpdate);
    response = restTemplate.exchange(URI.create("/shilleramericas/api/v1/persons/1"),
        HttpMethod.PUT, request, PersonDto.class);
    PersonDto personDtoUpdated = response.getBody();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(personDtoUpdated).extracting("name", "age", "gender").containsExactly(
        personToUpdate.getName(), personToUpdate.getAge(), personToUpdate.getGender());
    // The code must not change
    assertThat(personDtoUpdated).extracting("code").isEqualTo(personDto.getCode());
  }

  @Test
  public void testDeletePerson() {
    HttpEntity<PersonDto> request = new HttpEntity<>(new PersonDto("Juan",27));
    ResponseEntity<PersonDto> response = restTemplate.exchange(URI.create("/shilleramericas/api/v1/persons/3"),
        HttpMethod.DELETE, request, PersonDto.class);
    PersonDto activityDto = response.getBody();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    response =
        this.restTemplate.getForEntity(URI.create("/shilleramericas/api/v1/persons/3"), PersonDto.class);
    activityDto = response.getBody();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
