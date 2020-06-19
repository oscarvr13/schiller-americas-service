package com.shiller.americas.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.shiller.americas.dto.PersonDto;
import com.shiller.americas.dto.PersonDto.PersonDtoBuilder;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode
@Entity
public class Person {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  
  private String name;
  
  private Integer age;
  
  private String gender;
  
  private String code;
  
  private Date createdAt;

  public Integer getId() {
    return id;
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

  public Date getCreatedAt() {
    return createdAt;
  }
  
  private void setId(Integer id) {
    this.id = id;
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

  private void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public static PersonBuilder getBuilder() {
    return new PersonBuilder();
  }

  public static class PersonBuilder {
    private Integer id;
    
    private String name;
    
    private Integer age;
    
    private String gender;
    
    private String code;
    
    private Date createdAt;
  
    public Person build(PersonDto personDto) {
      Person person = new Person();
      person.setName(personDto.getName());
      person.setAge(personDto.getAge());
      person.setGender(personDto.getGender());
      person.setCode(personDto.getCode());
      person.setCreatedAt(personDto.getCreateDate());
      return person;
    }
    
    public PersonBuilder withId(Integer id) {
      this.id = id;
      return this;
    }
    
    public PersonBuilder withName(String name) {
      this.name = name;
      return this;
    }
    
    public PersonBuilder withAge(Integer age) {
      this.age = age;
      return this;
    }
    
    public PersonBuilder withGender(String gender) {
      this.gender = gender;
      return this;
    }
    
    public PersonBuilder withCode(String code) {
      this.code = code;
      return this;
    }
    
    public PersonBuilder withCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
      return this;
    }
    
    public Person build( ) {
      Person person = new Person();
      person.setId(this.id);
      person.setAge(this.age);
      person.setName(this.name);
      person.setGender(this.gender);
      person.setCode(this.code);
      person.setCreatedAt(this.createdAt);
      return person;
    }
  }

}
