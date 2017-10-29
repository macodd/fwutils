package de.higger.fwutils.npc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.higger.fwutils.common.service.RestControllerMappingService;
import de.higger.fwutils.npc.entity.NPC;
import de.higger.fwutils.npc.repository.NPCRepository;

@RestController
@RequestMapping("/api/v1/public/npcs")
public class NPCController {

	private static final String PARAM_NPC_ID = "npcId";

	@Autowired
	private NPCRepository npcRepository;

	@Autowired
	private RestControllerMappingService restControllerMappingService;

	@GetMapping
	public Iterable<NPC> getAllNPCs() {
		return npcRepository.findAll();
	}

	@GetMapping("/{npcId}")
	public ResponseEntity<NPC> getNPCById(@PathVariable(PARAM_NPC_ID) final long npcId) {

		final Optional<NPC> npc = npcRepository.findById(npcId);
		return restControllerMappingService.toResponseEntity(npc);
	}
}
