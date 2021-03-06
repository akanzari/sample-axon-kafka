package com.axonkafka.dto;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttachProductRequest {

	@NotBlank
	String catalogId;

	@NotBlank
	String name;

	String description;

	double price;

	double quantity;

}