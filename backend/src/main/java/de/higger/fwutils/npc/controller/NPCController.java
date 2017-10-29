package de.higger.fwutils.npc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.higger.fwutils.npc.service.NPCImportService;

@RestController
@RequestMapping("/api/v1/public/npc")
public class NPCController {

	@Autowired
	private NPCImportService npcImportService;

	@PostMapping("/import")
	public void importNPCs() {
		npcImportService.importNPCs();
	}
}
