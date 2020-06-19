package com.shiller.americas.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shiller.americas.entity.Person;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@ApiModel(value = "Person Model", description = "Values of each person")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonDto {

  /**
   * Person id
   */
  @ApiModelProperty(value = "Indicates the person id",
      example = "1", required = false, hidden = true)
  private Integer personId;

  /*
   * Person name.
   */
  @ApiModelProperty(value = "Indicates the person name",
      example = "Oscar", required = true)
  private String name;

  /**
   * Person age.
   */
  @ApiModelProperty(value = "Indicates the person age",
      example = "23", required = true)
  private Integer age;

  /**
   * Person gender.
   */
  @ApiModelProperty(value = "Indicates the person gender",
      example = "(M) or (F)", required = true)
  private String gender;

  /**
   * Alphabetic code regenerated randomly by using the method generateString.
   */
  @ApiModelProperty(value = "Indicates the person code generated randomly",
      example = "ABCDEFGHIJ", required = false, hidden = true)
  private String code;

  /**
   * Created date.
   */
  @ApiModelProperty(value = "Indicates the person create date",
      example = "2020-06-30", required = false, hidden = true)
  private Date createDate;
  
  public PersonDto() {
    
  }

  /**
   * Constructor to create the PersonDto with the mandatory values 
   * in the body request.
   * @param name Name of the person
   * @param age Age of the person
   */
  public PersonDto(String name, Integer age) {
    this.name = name;
    this.age = age;
    this.gender = "";
    this.code = generateString();
    createDate = new Date();
  }
  

  /**
   * Constructor to create the PersonDto with the all the values 
   * in the body request.
   * @param name Name of the person.
   * @param age Age of the person.
   * @param gender Gender of the person.
   */
  public PersonDto(String name, Integer age, String gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.code = generateString();
    this.createDate = new Date();
  }
   

  /**
   * Code to generate an alphabetical string 
   * with length 10. 
   * @return the random string generated.
   */
  private String generateString() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
    return generatedString;
  }

  public Integer getPersonId() {
    return personId;
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public String getCode() {
    return code;
  }

  public Date getCreateDate() {
    return createDate;
  }
  
  
  
  private void setPersonId(Integer personId) {
    this.personId = personId;
  }


  private void setName(String name) {
    this.name = name;
  }


  private void setAge(Integer age) {
    this.age = age;
  }


  private void setGender(String gender) {
    this.gender = gender;
  }


  private void setCode(String code) {
    this.code = code;
  }


  private void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  
  public static PersonDtoBuilder getBuilder() {
    return new PersonDtoBuilder();
  }

  public static class PersonDtoBuilder {
    
    public PersonDto build(Person person) {
      PersonDto personDto = new PersonDto();
      personDto.setPersonId(person.getId());
      personDto.setName(person.getName());
      personDto.setAge(person.getAge());
      personDto.setGender(person.getGender());
      personDto.setCode(person.getCode());
      personDto.setCreateDate(person.getCreatedAt());
      return personDto;
    }
  }
}
