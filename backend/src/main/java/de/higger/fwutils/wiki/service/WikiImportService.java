package de.higger.fwutils.wiki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.higger.fwutils.wiki.config.Category;
import de.higger.fwutils.wiki.config.WikiConfiguration;
import de.higger.fwutils.wiki.load.ImportBatchConfiguration;
import de.higger.fwutils.wiki.parse.item.AbilityParser;
import de.higger.fwutils.wiki.parse.item.ArmParser;
import de.higger.fwutils.wiki.parse.item.ArmType;
import de.higger.fwutils.wiki.parse.item.ItemParser;
import de.higger.fwutils.wiki.parse.item.NPCParser;
import de.higger.fwutils.wiki.parse.item.ParseItem;

@Service
public class WikiImportService {

	private static final Logger LOG = LoggerFactory.getLogger(WikiImportService.class);

	private final static String UPSERT_ABILITY_SQL = "INSERT INTO ability (id, url, name, base_time, max_level) VALUES (nextval('ability_seq'), :url, :label, :baseTime, :maxLevel)"
			+ "ON CONFLICT on constraint ability_url_un DO UPDATE SET update_time = now(), row_version = ability.row_version + 1, "
			+ "name = EXCLUDED.name, base_time = EXCLUDED.base_time, max_level = EXCLUDED.max_level "
			+ "WHERE ability.name != EXCLUDED.name OR ability.base_time != EXCLUDED.base_time OR ability.max_level != EXCLUDED.max_level";

	private final static String UPSERT_NPC_SQL = "INSERT INTO npc (id, url, name, min_strength, max_strength, min_health, max_health, dropped_money) VALUES (nextval('npc_seq'), :url, :label, :minStrength, :maxStrength, :minHealth, :maxHealth, :droppedMoney) "
			+ "ON CONFLICT on constraint npc_url_un DO UPDATE SET update_time = now(), row_version = npc.row_version + 1, "
			+ "name = EXCLUDED.name, min_strength = EXCLUDED.min_strength, max_strength = EXCLUDED.max_strength, min_health = EXCLUDED.min_health, max_health = EXCLUDED.max_health, dropped_money = EXCLUDED.dropped_money "
			+ "where npc.name != EXCLUDED.name OR npc.min_strength != EXCLUDED.min_strength OR npc.max_strength != EXCLUDED.max_strength OR npc.min_health != EXCLUDED.min_health OR npc.max_health != EXCLUDED.max_health OR npc.dropped_money != EXCLUDED.dropped_money";

	private final static String UPSERT_OFFENCE_ARM_SQL = "INSERT INTO offence_arm (id, url, name, strength, is_nondurable, required_power, required_intelligence, required_courses) VALUES (nextval('offence_arm_seq'), :url, :label, :strength, :isNondurable, :requiredPower, :requiredIntelligence, :requiredCourses) "
			+ "ON CONFLICT on constraint offence_arm_un DO UPDATE SET update_time = now(), row_version = offence_arm.row_version + 1, "
			+ "name = EXCLUDED.name, strength = EXCLUDED.strength, is_nondurable = EXCLUDED.is_nondurable, required_power = EXCLUDED.required_power, required_intelligence = EXCLUDED.required_intelligence, required_courses = EXCLUDED.required_courses "
			+ "where offence_arm.name != EXCLUDED.name OR offence_arm.strength != EXCLUDED.strength OR offence_arm.is_nondurable != EXCLUDED.is_nondurable OR offence_arm.required_power != EXCLUDED.required_power OR offence_arm.required_intelligence != EXCLUDED.required_intelligence OR offence_arm.required_courses != EXCLUDED.required_courses";

	private final static String UPSERT_DEFENCE_ARM_SQL = "INSERT INTO defence_arm (id, url, name, strength, is_nondurable, required_power, required_intelligence, required_courses) VALUES (nextval('defence_arm_seq'), :url, :label, :strength, :isNondurable, :requiredPower, :requiredIntelligence, :requiredCourses) "
			+ "ON CONFLICT on constraint defence_arm_un DO UPDATE SET update_time = now(), row_version = defence_arm.row_version + 1, "
			+ "name = EXCLUDED.name, strength = EXCLUDED.strength, is_nondurable = EXCLUDED.is_nondurable, required_power = EXCLUDED.required_power, required_intelligence = EXCLUDED.required_intelligence, required_courses = EXCLUDED.required_courses "
			+ "where defence_arm.name != EXCLUDED.name OR defence_arm.strength != EXCLUDED.strength OR defence_arm.is_nondurable != EXCLUDED.is_nondurable OR defence_arm.required_power != EXCLUDED.required_power OR defence_arm.required_intelligence != EXCLUDED.required_intelligence OR defence_arm.required_courses != EXCLUDED.required_courses";

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private WikiConfiguration config;

	@Autowired
	private ImportBatchConfiguration importBatchConfiguration;

	public void importAbilities() {

		final Category category = config.getCategories().getAbilities();
		importCategory("ability", new AbilityParser(), category, UPSERT_ABILITY_SQL);
	}

	public void importNPCs() {

		final Category category = config.getCategories().getNpcs();
		importCategory("npc", new NPCParser(), category, UPSERT_NPC_SQL);
	}

	public void importOffenceArms() {

		final Category category = config.getCategories().getOffenceArms();
		importCategory("offence-arm", new ArmParser(ArmType.OFFENCE), category, UPSERT_OFFENCE_ARM_SQL);
	}

	public void importDefenceArms() {

		final Category category = config.getCategories().getDefenceArms();
		importCategory("defence-arm", new ArmParser(ArmType.DEFENCE), category, UPSERT_DEFENCE_ARM_SQL);
	}

	private void importCategory(final String jobNameExtension, final ItemParser<? extends ParseItem> itemParser,
			final Category category, final String upsertStatement) {

		final Job job = importBatchConfiguration.createJob(jobNameExtension, itemParser, category, upsertStatement);

		try {
			final JobParameters jobParameters = new JobParametersBuilder()
					// Add unique parameter to allow more than one execution
					.addLong("time", System.currentTimeMillis()).toJobParameters();
			jobLauncher.run(job, jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			LOG.error("Failed to import items: ", e);
		}
	}
}
