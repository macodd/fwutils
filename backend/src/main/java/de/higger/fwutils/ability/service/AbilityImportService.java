package de.higger.fwutils.ability.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.higger.fwutils.ability.parse.item.AbilityParser;
import de.higger.fwutils.wiki.config.Category;
import de.higger.fwutils.wiki.config.WikiConfiguration;
import de.higger.fwutils.wiki.service.WikiImportService;

@Service
public class AbilityImportService {

	private static final Logger LOG = LoggerFactory.getLogger(AbilityImportService.class);

	private final static String UPSERT_ABILITY_SQL = "INSERT INTO ability (id, url, name, base_time, max_level) VALUES (nextval('ability_seq'), :url, :label, :baseTime, :maxLevel)"
			+ "ON CONFLICT on constraint ability_url_un DO UPDATE SET update_time = now(), row_version = ability.row_version + 1, "
			+ "name = EXCLUDED.name, base_time = EXCLUDED.base_time, max_level = EXCLUDED.max_level "
			+ "WHERE ability.name != EXCLUDED.name OR ability.base_time != EXCLUDED.base_time OR ability.max_level != EXCLUDED.max_level "
			+ "OR ability.name is null and EXCLUDED.name is not null OR ability.base_time is null and EXCLUDED.base_time is not null OR ability.max_level is null and EXCLUDED.max_level is not null";

	@Autowired
	private WikiConfiguration config;

	@Autowired
	private WikiImportService wikiImportService;

	@Scheduled(cron = "${wiki.categories.abilities.cron-schedule}")
	public void importAbilities() {
		LOG.info("Start sync abilities");
		final Category category = config.getCategories().getAbilities();
		wikiImportService.importCategory("ability", new AbilityParser(), category, UPSERT_ABILITY_SQL);
	}
}
