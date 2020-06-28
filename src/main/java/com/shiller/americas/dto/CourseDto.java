package com.shiller.americas.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(value = "Course Model", description = "Values from each course")
public class CourseDto {

  @ApiModelProperty(value = "Id of the course", example = "1", required = true)
  private int id;

  @ApiModelProperty(value = "Name of the course", example = "Course 1",  required = true)
  private String name;

  @ApiModelProperty(value = "Duration of the course in weeks only integer values",
      example = "3", required = true)
  private int weeksDuration;

  private int totalActivityPoints;


}
