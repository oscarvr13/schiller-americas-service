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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;;

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

  @Value("${path.request.post.person}")
  private String postUri;

  @Value("${path.request.put.person}")
  private String putUri;

  @Value("${path.request.delete.person}")
  private String deleteUri;

  @Test
  public void testGetPersons() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    PersonDto[] personDtos = {
        new PersonDto("Oscar", 32), new PersonDto("Jorge", 33)
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
    String personId = "1";
    String uri = generalUri + getUri;
    uri = uri.replace("{personId}", personId);
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
    PersonDto personDtoReceived =  mapper.readValue(result, PersonDto.class);
    assertThat(personDtoReceived).extracting("name", "age","gender")
        .containsExactly(personDtoExpected.getName(), personDtoExpected.getAge(), personDtoExpected.getGender());
    assertThat(personDtoReceived).extracting("code", "createDate")
    .isNotEmpty().isNotNull()
        .containsExactly(personDtoExpected.getCode(),  personDtoExpected.getCreateDate());
  }
  
  @Test
  public void testCreatePersonWithAllAttributes() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    PersonDto personDtoToCreate = new PersonDto("Oscar", 32, "M");
    String uri = generalUri + postUri;
    String expectedJson = mapper.writeValueAsString(personDtoToCreate);
    System.out.println("ExpectedJson: "+expectedJson);
    when(personService.createPerson(personDtoToCreate))
        .thenReturn(personDtoToCreate);
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
    String headerLocation = response.getResponse().getHeader("Location").toString();
    PersonDto personDtoCreated =  mapper.readValue(result, PersonDto.class);
    assertThat(personDtoCreated).extracting("name", "age", "gender")
        .containsExactly(personDtoToCreate.getName(), personDtoToCreate.getAge(),
            personDtoToCreate.getGender());
    assertThat(personDtoCreated).extracting("code", "createDate")
    .isNotEmpty().isNotNull()
        .containsExactly(personDtoToCreate.getCode(), personDtoToCreate.getCreateDate());
    assertThat(headerLocation).contains(generalUri+getAllUri);
  }

  @Test
  public void testCreatePersonWithNoEnoughAttributes() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    PersonDto personDtoToCreate = new PersonDto("Oscar", null);
    String uri = generalUri + postUri;
    String expectedJson = mapper.writeValueAsString(personDtoToCreate);
    System.out.println("ExpectedJson: "+expectedJson);
    when(personService.createPerson(personDtoToCreate))
        .thenReturn(personDtoToCreate);
    RequestBuilder request =
        MockMvcRequestBuilders.post(uri)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(expectedJson);
    MvcResult response =
        mockMvc.perform(request)
            .andExpect(status().isBadRequest())
            .andReturn();
    Exception exception = response.getResolvedException();
    System.out.println("Response: "+exception.toString());
  }

  @Test
  public void testCreatePersonWithMinimumAttributes() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    PersonDto personDtoToCreate = new PersonDto("Oscar", 32);
    String uri = generalUri + postUri;
    String expectedJson = mapper.writeValueAsString(personDtoToCreate);
    System.out.println("ExpectedJson: "+expectedJson);
    when(personService.createPerson(personDtoToCreate))
        .thenReturn(personDtoToCreate);
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
    String headerLocation = response.getResponse().getHeader("Location").toString();
    PersonDto personDtoCreated =  mapper.readValue(result, PersonDto.class);
    assertThat(personDtoCreated).extracting("name", "age", "gender")
        .containsExactly(personDtoToCreate.getName(), personDtoToCreate.getAge(), "");
    assertThat(personDtoCreated).extracting("code", "createDate")
        .isNotEmpty().isNotNull()
        .containsExactly(personDtoToCreate.getCode(), personDtoToCreate.getCreateDate());
    assertThat(headerLocation).contains(generalUri+getAllUri);
  }
  
  @Test
  public void testUpdatePerson() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    PersonDto personDtoToUpdate = new PersonDto("Oscar", 32,"M");
    String personId = "1";
    String uri = generalUri + putUri;
    uri = uri.replace("{personId}", personId);
    String expectedJson = mapper.writeValueAsString(personDtoToUpdate);
    System.out.println("ExpectedJson: "+expectedJson);
    when(personService.updatePerson(Integer.valueOf(personId), personDtoToUpdate))
        .thenReturn(personDtoToUpdate);
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
    PersonDto personDtoUpdated =  mapper.readValue(result, PersonDto.class);
    assertThat(personDtoUpdated).extracting("name", "age", "gender")
        .containsExactly(personDtoToUpdate.getName(), personDtoToUpdate.getAge(),
            personDtoToUpdate.getGender());
    assertThat(personDtoUpdated).extracting("code", "createDate")
        .isNotEmpty().isNotNull()
        .containsExactly(personDtoToUpdate.getCode(), personDtoToUpdate.getCreateDate());
  }

  @Test
  public void testUpdatePersonWithMinimumAttributes() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    PersonDto personDtoToUpdate = new PersonDto("Oscar", 32);
    String personId = "1";
    String uri = generalUri + putUri;
    uri = uri.replace("{personId}", personId);
    String expectedJson = mapper.writeValueAsString(personDtoToUpdate);
    System.out.println("ExpectedJson: "+expectedJson);
    when(personService.updatePerson(Integer.valueOf(personId), personDtoToUpdate))
        .thenReturn(personDtoToUpdate);
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
    PersonDto personDtoUpdated =  mapper.readValue(result, PersonDto.class);
    assertThat(personDtoUpdated).extracting("name", "age", "gender")
        .containsExactly(personDtoToUpdate.getName(), personDtoToUpdate.getAge(), "");
    assertThat(personDtoUpdated).extracting("code", "createDate")
        .isNotEmpty().isNotNull()
        .containsExactly(personDtoToUpdate.getCode(), personDtoToUpdate.getCreateDate());
  }
  
  @Test
  public void testDeletePerson() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    String personId = "1";
    String uri = generalUri + deleteUri;
    uri = uri.replace("{personId}", personId);
    when(personService.deletePerson(Integer.valueOf(personId))).thenReturn(true);
    RequestBuilder request =
        MockMvcRequestBuilders.delete(uri);
    MvcResult response =
        mockMvc.perform(request)
        .andExpect(status().isNoContent())
        .andReturn();
    }
}
