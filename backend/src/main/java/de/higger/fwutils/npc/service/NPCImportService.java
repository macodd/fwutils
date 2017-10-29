package de.higger.fwutils.npc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.higger.fwutils.npc.parse.item.NPCParser;
import de.higger.fwutils.wiki.config.Category;
import de.higger.fwutils.wiki.config.WikiConfiguration;
import de.higger.fwutils.wiki.service.WikiImportService;

@Service
public class NPCImportService {

	private static final Logger LOG = LoggerFactory.getLogger(NPCImportService.class);

	private final static String UPSERT_NPC_SQL = "INSERT INTO npc (id, url, name, min_strength, max_strength, min_health, max_health, dropped_money) VALUES (nextval('npc_seq'), :url, :label, :minStrength, :maxStrength, :minHealth, :maxHealth, :droppedMoney) "
			+ "ON CONFLICT on constraint npc_url_un DO UPDATE SET update_time = now(), row_version = npc.row_version + 1, "
			+ "name = EXCLUDED.name, min_strength = EXCLUDED.min_strength, max_strength = EXCLUDED.max_strength, min_health = EXCLUDED.min_health, max_health = EXCLUDED.max_health, dropped_money = EXCLUDED.dropped_money "
			+ "where npc.name != EXCLUDED.name OR npc.min_strength != EXCLUDED.min_strength OR npc.max_strength != EXCLUDED.max_strength OR npc.min_health != EXCLUDED.min_health OR npc.max_health != EXCLUDED.max_health OR npc.dropped_money != EXCLUDED.dropped_money "
			+ "OR npc.name is null and EXCLUDED.name is not null OR npc.min_strength is null and EXCLUDED.min_strength is not null OR npc.max_strength is null and EXCLUDED.max_strength is not null OR npc.min_health is null and EXCLUDED.min_health is not null OR npc.max_health is null and EXCLUDED.max_health is not null OR npc.dropped_money is null and EXCLUDED.dropped_money is not null";

	@Autowired
	private WikiConfiguration config;

	@Autowired
	private WikiImportService wikiImportService;

	@Scheduled(cron = "${wiki.categories.npcs.cron-schedule}")
	public void importNPCs() {
		LOG.info("Start sync npcs");
		final Category category = config.getCategories().getNpcs();
		wikiImportService.importCategory("npc", new NPCParser(), category, UPSERT_NPC_SQL);
	}
}
