package de.higger.fwutils.arm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.higger.fwutils.arm.service.ArmImportService;

@RestController
@RequestMapping("/api/v1/public/arm")
public class ArmController {

	@Autowired
	private ArmImportService armImportService;

	@PostMapping("/import-defence")
	public void importDefenceArms() {
		armImportService.importDefenceArms();
	}

	@PostMapping("/import-offence")
	public void importOffenceArms() {
		armImportService.importOffenceArms();
	}
}
