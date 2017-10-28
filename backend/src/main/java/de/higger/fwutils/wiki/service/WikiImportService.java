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
		importCategory("npc", new NPCParser(), category, null);
	}

	public void importOffenceArms() {

		final Category category = config.getCategories().getOffenceArms();
		importCategory("offence-arm", new ArmParser(ArmType.OFFENCE), category, null);
	}

	public void importDefenceArms() {

		final Category category = config.getCategories().getDefenceArms();
		importCategory("defence-arm", new ArmParser(ArmType.DEFENCE), category, null);
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
