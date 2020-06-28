package com.shiller.americas.controller.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shiller.americas.dto.CourseDto;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@RequestMapping(name = "${path.request.mapping.lms.controller}")
public class CourseClientFallBack implements CourseClient{

  @Override
  public ResponseEntity<List<CourseDto>> getAllCourses() {
    System.out.println("FallBack method");
    return null;
  }
}
