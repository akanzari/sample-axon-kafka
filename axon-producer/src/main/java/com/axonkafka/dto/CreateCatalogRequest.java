package com.axonkafka.dto;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCatalogRequest {

	@NotBlank(message = "Catalog name must not be empty or null")
	String name;

	@NotBlank(message = "Description must not be empty or null")
	String description;

}