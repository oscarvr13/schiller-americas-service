package com.shiller.americas.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiller.americas.dto.PersonDto;
import com.shiller.americas.service.PersonService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonController.class)
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @Value("${path.person.controller}")
  private String generalUri;
  
  @Value("${path.request.get.persons}")
  private String getAllUri;
  
  @Value("${path.request.get.person}")
  private String getUri;
  
  
  
  @Test
  public void testGetPersons() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    PersonDto[] personDtos = {
        new PersonDto("Oscar", 32, "M"), new PersonDto("Jorge", 33, "M")
        };
    String uri = generalUri + getAllUri;
    String expectedJson = mapper.writeValueAsString(personDtos);
    when(personService.getAllPersons()).thenReturn(Arrays.asList(personDtos));
    RequestBuilder request =
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
    MvcResult response =
        mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
    String result = response.getResponse().getContentAsString();
    
    List<PersonDto> listPersonDto = Arrays.asList(
        mapper.readValue(result, PersonDto[].class));
    assertThat(listPersonDto).hasSize(2);
    assertThat(listPersonDto).extracting("name")
        .containsExactly("Oscar", "Jorge");
    assertThat(listPersonDto).extracting("age")
        .containsExactly(32, 33);
  }
  
  @Test
  public void testGetPerson() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    PersonDto personDtoExpected = new PersonDto("Oscar", 32, "M");
    String uri = "/shilleramericas/api/v1/persons/1";
    String expectedJson = mapper.writeValueAsString(personDtoExpected);
    System.out.println("ExpectedJson: "+expectedJson);
    when(personService.getPerson(1)).thenReturn(personDtoExpected);
    RequestBuilder request =
        MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
    MvcResult response =
        mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
    String result = response.getResponse().getContentAsString();
    PersonDto personDto =  mapper.readValue(result, PersonDto.class);
    assertThat(personDto).extracting("name", "age")
        .containsExactly(personDtoExpected.getName(), personDtoExpected.getAge());
    assertThat(personDto).extracting("code", "createDate")
    .isNotEmpty().isNotNull();
  }
  
  @Test
  public void testCreatePerson() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    PersonDto personToCreate = new PersonDto("Oscar", 32, "M");
    String uri = "/shilleramericas/api/v1/persons";
    String expectedJson = mapper.writeValueAsString(personToCreate);
    System.out.println("ExpectedJson: "+expectedJson);
    when(personService.createPerson(any(PersonDto.class)))
        .thenReturn(personToCreate);
    RequestBuilder request =
        MockMvcRequestBuilders.post(uri)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(expectedJson);
    MvcResult response =
        mockMvc.perform(request)
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
    String result = response.getResponse().getContentAsString();
    PersonDto personDto =  mapper.readValue(result, PersonDto.class);
    assertThat(personDto).extracting("name", "age")
        .containsExactly(personToCreate.getName(), personToCreate.getAge());
    assertThat(personDto).extracting("code", "createDate")
    .isNotEmpty().isNotNull();
  }
  
  @Test
  public void testUpdatePerson() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    PersonDto personToUpdate = new PersonDto("Oscar", 32, "M");
    String uri = "/shilleramericas/api/v1/persons/1";
    String expectedJson = mapper.writeValueAsString(personToUpdate);
    System.out.println("ExpectedJson: "+expectedJson);
    when(personService.updatePerson(anyInt(), (any(PersonDto.class))))
        .thenReturn(personToUpdate);
    RequestBuilder request =
        MockMvcRequestBuilders.put(uri)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(expectedJson);
    MvcResult response =
        mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();
    String result = response.getResponse().getContentAsString();
    PersonDto personDto =  mapper.readValue(result, PersonDto.class);
    assertThat(personDto).extracting("name", "age")
        .containsExactly(personToUpdate.getName(), personToUpdate.getAge());
    assertThat(personDto).extracting("code", "createDate")
    .isNotEmpty().isNotNull();
  }
  
  @Test
  public void testDeletePerson() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String uri = "/shilleramericas/api/v1/persons/1";
    when(personService.deletePerson(1)).thenReturn(true);
    RequestBuilder request =
        MockMvcRequestBuilders.delete(uri);
    MvcResult response =
        mockMvc.perform(request)
        .andExpect(status().isNoContent())
        .andReturn();
    }
}
