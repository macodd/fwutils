package de.higger.fwutils.common.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RestControllerMappingService {

	public <T> ResponseEntity<T> toResponseEntity(final Optional<T> entity) {

		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
