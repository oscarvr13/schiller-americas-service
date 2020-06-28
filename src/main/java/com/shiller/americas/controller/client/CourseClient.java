package com.shiller.americas.controller.client;

import com.shiller.americas.config.feign.FeignCourseConfig;
import com.shiller.americas.config.ribbon.RibbonCourseConfig;
import com.shiller.americas.dto.CourseDto;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "course-micro", primary = true
    //, url = "http://localhost:8081",
    , configuration = FeignCourseConfig.class//, fallback = CourseClientFallBack.class
     )
@RibbonClient(name = "course-micro" //configuration = RibbonCourseConfig.class
 )
@RequestMapping(value = "${path.request.mapping.lms.controller}")
public interface CourseClient {

  @GetMapping(value = "${path.request.mapping.request.courses}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<CourseDto>> getAllCourses();

}


