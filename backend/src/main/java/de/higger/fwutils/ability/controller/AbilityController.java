package de.higger.fwutils.ability.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.higger.fwutils.ability.service.AbilityImportService;

@RestController
@RequestMapping("/api/v1/public/ability")
public class AbilityController {

	@Autowired
	private AbilityImportService abilityImportService;

	@PostMapping("/import")
	public void importAbilities() {
		abilityImportService.importAbilities();
	}
}
