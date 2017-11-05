package de.higger.fwutils.arm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.higger.fwutils.arm.entity.DefenceArm;
import de.higger.fwutils.arm.entity.OffenceArm;
import de.higger.fwutils.arm.repository.DefenceArmRepository;
import de.higger.fwutils.arm.repository.OffenceArmRepository;
import de.higger.fwutils.arm.service.ArmImportService;
import de.higger.fwutils.common.service.RestControllerMappingService;

@RestController
@RequestMapping("/api/v1/public")
public class ArmController {

	private static final String PARAM_OFFENCE_ARM_ID = "offenceArmId";
	private static final String PARAM_DEFENCE_ARM_ID = "defenceArmId";

	@Autowired
	private OffenceArmRepository offenceArmRepository;

	@Autowired
	private DefenceArmRepository defenceArmRepository;

	@Autowired
	private RestControllerMappingService restControllerMappingService;

	@Autowired
	private ArmImportService armImportService;

	@GetMapping("/offence-arms")
	public Iterable<OffenceArm> getAllOffenceArms() {
		return offenceArmRepository.findAll();
	}

	@GetMapping("/offence-arms/{offenceArmId}")
	public ResponseEntity<OffenceArm> getOffenceArmById(@PathVariable(PARAM_OFFENCE_ARM_ID) final long offenceArmId) {

		final Optional<OffenceArm> offenceArm = offenceArmRepository.findById(offenceArmId);
		return restControllerMappingService.toResponseEntity(offenceArm);
	}

	@GetMapping("/defence-arms")
	public Iterable<DefenceArm> getAllDefenceArms() {
		return defenceArmRepository.findAll();
	}

	@GetMapping("/defence-arms/{defenceArmId}")
	public ResponseEntity<DefenceArm> getDefenceArmById(@PathVariable(PARAM_DEFENCE_ARM_ID) final long defenceArmId) {

		final Optional<DefenceArm> defenceArm = defenceArmRepository.findById(defenceArmId);
		return restControllerMappingService.toResponseEntity(defenceArm);
	}

	@GetMapping("/defence-arms/import")
	public void importDefenceArms() {
		armImportService.importDefenceArms();
	}

	@GetMapping("/offence-arms/import")
	public void importOffenceArms() {
		armImportService.importOffenceArms();
	}
}
