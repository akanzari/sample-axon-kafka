package com.axonkafka.dto;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AggregateHistoryDTO {

	String name;

	Object event;

	long sequenceNumber;

	Instant timestamp;

}