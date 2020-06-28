package com.shiller.americas.controller.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.shiller.americas.dto.CourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

  private final CourseClient courseClient;

  @HystrixCommand(fallbackMethod = "getAllCoursesFallBack",
      commandKey = "getAllCoursesCommand",
      threadPoolKey = "getCoursesThreadPool",
      commandProperties = {
         // @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
          @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
          @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
          @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "100"),
          @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
      },
      threadPoolProperties = {
          @HystrixProperty(name = "coreSize", value = "2"),
          @HystrixProperty(name = "maxQueueSize", value = "3"),
          @HystrixProperty(name = "queueSizeRejectionThreshold", value = "3"),
          @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "40000")
      })

  public ResponseEntity<List<CourseDto>> getAllCourses() {
    return courseClient.getAllCourses();
  }

  public ResponseEntity<List<CourseDto>> getAllCoursesFallBack() {
    System.out.println("Entra al falback getAllCoursesFallBack");
    return null;
  }


}
