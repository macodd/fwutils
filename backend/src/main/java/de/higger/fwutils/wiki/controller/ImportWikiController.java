package de.higger.fwutils.wiki.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.higger.fwutils.wiki.service.WikiImportService;

@RestController
@RequestMapping("/api/v1/wiki")
public class ImportWikiController {

	private static final Logger LOG = LoggerFactory.getLogger(ImportWikiController.class);

	@Autowired
	private WikiImportService wikiImportService;

	@PostMapping("/test")
	public void test() throws IOException {

		wikiImportService.importAbilities();
		// wikiImportService.importNPCs();
		// wikiImportService.importOffenceArms();
		// wikiImportService.importDefenceArms();
	}
}
