package de.higger.fwutils.wiki.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.higger.fwutils.wiki.service.WikiImportService;

@Component
public class ImportItemFromWikiScheduler {

	private static final Logger LOG = LoggerFactory.getLogger(ImportItemFromWikiScheduler.class);

	@Autowired
	private WikiImportService wikiImportService;

	@Scheduled(cron = "${wiki.categories.npcs.cron-schedule}")
	public void syncNPCs() {
		LOG.info("Start sync npcs");
		wikiImportService.importNPCs();
	}

	@Scheduled(cron = "${wiki.categories.abilities.cron-schedule}")
	public void syncAbilities() {
		LOG.info("Start sync abilities");
		wikiImportService.importAbilities();
	}

	@Scheduled(cron = "${wiki.categories.offence-arms.cron-schedule}")
	public void syncOffenceArms() {
		LOG.info("Start sync offence arms");
		wikiImportService.importOffenceArms();
	}

	@Scheduled(cron = "${wiki.categories.defence-arms.cron-schedule}")
	public void syncDefenceArms() {
		LOG.info("Start sync defence arms");
		wikiImportService.importDefenceArms();
	}
}
