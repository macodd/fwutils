package de.higger.fwutils.ability.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.higger.fwutils.ability.entity.Ability;
import de.higger.fwutils.ability.repository.AbilityRepository;
import de.higger.fwutils.ability.service.AbilityImportService;
import de.higger.fwutils.common.service.RestControllerMappingService;

@RestController
@RequestMapping("/api/v1/public/abilities")
public class AbilityController {

	private static final String PARAM_ABILITY_ID = "abilityId";

	@Autowired
	private AbilityRepository abilityRepository;

	@Autowired
	private AbilityImportService abilityImportService;

	@Autowired
	private RestControllerMappingService restControllerMappingService;

	@GetMapping
	public Iterable<Ability> getAllAbilities() {
		return abilityRepository.findAll();
	}

	@GetMapping("/{abilityId}")
	public ResponseEntity<Ability> getAbilityById(@PathVariable(PARAM_ABILITY_ID) final long abilityId) {

		final Optional<Ability> ability = abilityRepository.findById(abilityId);
		return restControllerMappingService.toResponseEntity(ability);
	}

	@GetMapping("/import")
	public void importAbilities() {
		abilityImportService.importAbilities();
	}
}
