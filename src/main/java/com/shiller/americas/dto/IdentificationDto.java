package com.shiller.americas.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Identification Model", description = "Values of each identification")
public class IdentificationDto {
  
  /**
   * Identification id.
   */
  @ApiModelProperty(value = "Indicates the identification id",
      example = "1", required = false, hidden = true)
  private Integer identificationId;

  /**
   * Identification name.
   */
  @ApiModelProperty(value = "Indicates the identification name",
      example = "INE", required = true)
  private String name;

  /**
   * Identification description.
   */
  @ApiModelProperty(value = "Indicates the identification description",
      example = "INE", required = false)
  private String description;

  /**
   * Date when the identification was created.
   */

  @ApiModelProperty(value = "Indicates the identification create date",
      example = "2020-06-15 (yyyy-MM-dd)", required = false, hidden = true)
  private LocalDate createDate;

  /**
   * Constructor to create the IdentificationDto with all the values.
   * @param name Name of the identification.
   * @param description Description of the identification.
   */
  public IdentificationDto(String name, String description) {
    this.name = name;
    this.description = description;
    this.createDate = LocalDate.now();
  }
  
  /**
   * Constructor to create the IdentificationDto without description.
   * @param name Name of the identification.
   */
  public IdentificationDto(String name) {
    this.name = name;
    this.createDate = LocalDate.now();
  }  
}
