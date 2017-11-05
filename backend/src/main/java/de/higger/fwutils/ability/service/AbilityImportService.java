package de.higger.fwutils.ability.service;

import java.util.UUID;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AbilityImportService {

	public final static String UPSERT_ABILITY_SQL = "INSERT INTO ability (id, url, name, base_time, max_level) VALUES (nextval('ability_seq'), :url, :label, :baseTime, :maxLevel)"
			+ "ON CONFLICT on constraint ability_url_un DO UPDATE SET update_time = now(), row_version = ability.row_version + 1, "
			+ "name = EXCLUDED.name, base_time = EXCLUDED.base_time, max_level = EXCLUDED.max_level "
			+ "WHERE ability.name != EXCLUDED.name OR ability.base_time != EXCLUDED.base_time OR ability.max_level != EXCLUDED.max_level "
			+ "OR ability.name is null and EXCLUDED.name is not null OR ability.base_time is null and EXCLUDED.base_time is not null OR ability.max_level is null and EXCLUDED.max_level is not null";

	private static final Logger LOG = LoggerFactory.getLogger(AbilityImportService.class);
	private static final String JOB_PARAM_KEY_UNIQUE_ID = "uuid";

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job importAbilitiesJob;

	@Scheduled(cron = "${wiki.categories.abilities.cron-schedule}")
	public void importAbilities() {
		LOG.info("Start sync abilities");

		final JobParameters jobParameters = new JobParametersBuilder()
				.addString(JOB_PARAM_KEY_UNIQUE_ID, UUID.randomUUID().toString()).toJobParameters();
		try {
			jobLauncher.run(importAbilitiesJob, jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			LOG.error("Failed to update abilities", e);
		}
	}
}
