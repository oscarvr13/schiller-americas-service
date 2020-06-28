package com.shiller.americas.controller.api;

import java.util.List;

import com.shiller.americas.dto.CourseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.shiller.americas.dto.PersonDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;

@RequestMapping(value = "${path.person.controller}")
public interface PersonApi {

  @ApiOperation(value = "To retrieve all persons",
      notes = "This endpoint find and retrieve all the available persons",
      tags = "retrieve-all-persons", response = ResponseEntity.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "Accept", dataType = "String", paramType = "header",
        value = "Media Type that is required for the body response")
    })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Send all the persons found"),
      @ApiResponse(code = 406, message = "The response media type is not supported")
  })
  @GetMapping(value = "${path.request.get.persons}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<PersonDto>> getPersons();
  
  @ApiOperation(value = "To retrieve a person",
      notes = "This endpoint find the person with the specified id",
      tags = "retrieve-person-by-id", response = ResponseEntity.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "Accept", dataType = "String", paramType = "header",
        value = "Media Type that is required for the body response")
    })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Person found"),
      @ApiResponse(code = 404, message = "The person was not found"),
      @ApiResponse(code = 406, message = "The response media type is not supported")
  })
  @GetMapping(value = "${path.request.get.person}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PersonDto> getPerson(
      @ApiParam(value = "person id", required = true, example = "123")
      @PathVariable("personId") int personId);
  
  @ApiOperation(value = "To create a person",
      notes = "This endpoint create a person with an autogenerated id",
      tags = "create-person", response = ResponseEntity.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "Accept", dataType = "String", paramType = "header",
        value = "Media Type that is required for the body response"),
    @ApiImplicitParam(name = "Content-Type", dataType = "String", paramType = "header",
    value = "Media Type that is required for the body request")
    })
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Person created"),
      @ApiResponse(code = 400, message = "The body request must have all the mandatory values"),
      @ApiResponse(code = 406, message = "The response media type is not supported"),
      @ApiResponse(code = 415, message = "The request media type is not supported")
  })
  @PostMapping(value = "${path.request.post.person}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PersonDto> createPerson(@RequestBody @Valid PersonDto personToUpdate);
  
  
  @ApiOperation(value = "To update a person",
      notes = "This endpoint update the person with the specified id",
      tags = "update-person-by-id", response = ResponseEntity.class)
  @ApiImplicitParams({
    @ApiImplicitParam(name = "Accept", dataType = "String", paramType = "header",
        value = "Media Type that is required for the body response"),
    @ApiImplicitParam(name = "Content-Type", dataType = "String", paramType = "header",
    value = "Media Type that is required for the body request")
    })
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Person updated"),
      @ApiResponse(code = 400, message = "The body request must have all the mandatory values"),
      @ApiResponse(code = 404, message = "The person was not found"),
      @ApiResponse(code = 406, message = "The response media type is not supported"),
      @ApiResponse(code = 415, message = "The request media type is not supported")
  })
  @PutMapping(value = "${path.request.put.person}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<PersonDto> updatePerson(
      @ApiParam(value = "person id", required = true, example = "123")
      @PathVariable ("personId") int personId,
      @RequestBody @Valid PersonDto personToUpdate);
  
  @ApiOperation(value = "To delete a person",
      notes = "This endpoint delete the person with the specified id",
      tags = "delete-person-by-id", response = ResponseEntity.class)
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Person deleted"),
      @ApiResponse(code = 404, message = "The person was not found")
      })
  @DeleteMapping(value = "${path.request.delete.person}")
  ResponseEntity<PersonDto> deletePerson(
      @ApiParam(value = "person id", required = true, example = "123")
      @PathVariable ("personId") int personId);

  @GetMapping(value = "${path.request.get.courses}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<CourseDto>> getCourses();
}


