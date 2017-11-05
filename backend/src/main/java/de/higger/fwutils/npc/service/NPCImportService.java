package de.higger.fwutils.npc.service;

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
public class NPCImportService {

	public final static String UPSERT_NPC_SQL = "INSERT INTO npc (id, url, name, min_strength, max_strength, min_health, max_health, dropped_money) VALUES (nextval('npc_seq'), :url, :label, :minStrength, :maxStrength, :minHealth, :maxHealth, :droppedMoney) "
			+ "ON CONFLICT on constraint npc_url_un DO UPDATE SET update_time = now(), row_version = npc.row_version + 1, "
			+ "name = EXCLUDED.name, min_strength = EXCLUDED.min_strength, max_strength = EXCLUDED.max_strength, min_health = EXCLUDED.min_health, max_health = EXCLUDED.max_health, dropped_money = EXCLUDED.dropped_money "
			+ "where npc.name != EXCLUDED.name OR npc.min_strength != EXCLUDED.min_strength OR npc.max_strength != EXCLUDED.max_strength OR npc.min_health != EXCLUDED.min_health OR npc.max_health != EXCLUDED.max_health OR npc.dropped_money != EXCLUDED.dropped_money "
			+ "OR npc.name is null and EXCLUDED.name is not null OR npc.min_strength is null and EXCLUDED.min_strength is not null OR npc.max_strength is null and EXCLUDED.max_strength is not null OR npc.min_health is null and EXCLUDED.min_health is not null OR npc.max_health is null and EXCLUDED.max_health is not null OR npc.dropped_money is null and EXCLUDED.dropped_money is not null";

	private static final Logger LOG = LoggerFactory.getLogger(NPCImportService.class);
	private static final String JOB_PARAM_KEY_UNIQUE_ID = "uuid";

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job importNpcsJob;

	@Scheduled(cron = "${wiki.categories.npcs.cron-schedule}")
	public void importNPCs() {
		LOG.info("Start sync npcs");

		final JobParameters jobParameters = new JobParametersBuilder()
				.addString(JOB_PARAM_KEY_UNIQUE_ID, UUID.randomUUID().toString()).toJobParameters();
		try {
			jobLauncher.run(importNpcsJob, jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			LOG.error("Failed to update abilities", e);
		}
	}
}
