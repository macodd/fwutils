package de.higger.fwutils.arm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import de.higger.fwutils.arm.parse.item.ArmParser;
import de.higger.fwutils.arm.parse.item.ArmType;
import de.higger.fwutils.wiki.config.Category;
import de.higger.fwutils.wiki.config.WikiConfiguration;
import de.higger.fwutils.wiki.service.WikiImportService;

@Service
public class ArmImportService {

	private static final Logger LOG = LoggerFactory.getLogger(ArmImportService.class);

	private final static String UPSERT_OFFENCE_ARM_SQL = "INSERT INTO offence_arm (id, url, name, strength, is_nondurable, required_power, required_intelligence, required_courses) VALUES (nextval('offence_arm_seq'), :url, :label, :strength, :isNondurable, :requiredPower, :requiredIntelligence, :requiredCourses) "
			+ "ON CONFLICT on constraint offence_arm_un DO UPDATE SET update_time = now(), row_version = offence_arm.row_version + 1, "
			+ "name = EXCLUDED.name, strength = EXCLUDED.strength, is_nondurable = EXCLUDED.is_nondurable, required_power = EXCLUDED.required_power, required_intelligence = EXCLUDED.required_intelligence, required_courses = EXCLUDED.required_courses "
			+ "where offence_arm.name != EXCLUDED.name OR offence_arm.strength != EXCLUDED.strength OR offence_arm.is_nondurable != EXCLUDED.is_nondurable OR offence_arm.required_power != EXCLUDED.required_power OR offence_arm.required_intelligence != EXCLUDED.required_intelligence OR offence_arm.required_courses != EXCLUDED.required_courses "
			+ "OR offence_arm.name is null and EXCLUDED.name is not null OR offence_arm.strength is null and EXCLUDED.strength is not null OR offence_arm.is_nondurable is null and EXCLUDED.is_nondurable is not null OR offence_arm.required_power is null and EXCLUDED.required_power is not null OR offence_arm.required_intelligence is null and EXCLUDED.required_intelligence is not null OR offence_arm.required_courses is null and EXCLUDED.required_courses is not null";

	private final static String UPSERT_DEFENCE_ARM_SQL = "INSERT INTO defence_arm (id, url, name, strength, is_nondurable, required_power, required_intelligence, required_courses) VALUES (nextval('defence_arm_seq'), :url, :label, :strength, :isNondurable, :requiredPower, :requiredIntelligence, :requiredCourses) "
			+ "ON CONFLICT on constraint defence_arm_un DO UPDATE SET update_time = now(), row_version = defence_arm.row_version + 1, "
			+ "name = EXCLUDED.name, strength = EXCLUDED.strength, is_nondurable = EXCLUDED.is_nondurable, required_power = EXCLUDED.required_power, required_intelligence = EXCLUDED.required_intelligence, required_courses = EXCLUDED.required_courses "
			+ "where defence_arm.name != EXCLUDED.name OR defence_arm.strength != EXCLUDED.strength OR defence_arm.is_nondurable != EXCLUDED.is_nondurable OR defence_arm.required_power != EXCLUDED.required_power OR defence_arm.required_intelligence != EXCLUDED.required_intelligence OR defence_arm.required_courses != EXCLUDED.required_courses "
			+ "OR defence_arm.name is null and EXCLUDED.name is not null OR defence_arm.strength is null and EXCLUDED.strength is not null OR defence_arm.is_nondurable is null and EXCLUDED.is_nondurable is not null OR defence_arm.required_power is null and EXCLUDED.required_power is not null OR defence_arm.required_intelligence is null and EXCLUDED.required_intelligence is not null OR defence_arm.required_courses is null and EXCLUDED.required_courses is not null";

	@Autowired
	private WikiConfiguration config;

	@Autowired
	private WikiImportService wikiImportService;

	@Scheduled(cron = "${wiki.categories.offence-arms.cron-schedule}")
	public void importOffenceArms() {
		LOG.info("Start sync offence arms");
		final Category category = config.getCategories().getOffenceArms();
		wikiImportService.importCategory("offence-arm", new ArmParser(ArmType.OFFENCE), category,
				UPSERT_OFFENCE_ARM_SQL);
	}

	@Scheduled(cron = "${wiki.categories.defence-arms.cron-schedule}")
	public void importDefenceArms() {
		LOG.info("Start sync defence arms");
		final Category category = config.getCategories().getDefenceArms();
		wikiImportService.importCategory("defence-arm", new ArmParser(ArmType.DEFENCE), category,
				UPSERT_DEFENCE_ARM_SQL);
	}
}
