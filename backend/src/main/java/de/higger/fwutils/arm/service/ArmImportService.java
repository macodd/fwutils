package de.higger.fwutils.arm.service;

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
public class ArmImportService {

	public final static String UPSERT_OFFENCE_ARM_SQL = "INSERT INTO offence_arm (id, url, name, strength, is_nondurable, required_power, required_intelligence, required_courses) VALUES (nextval('offence_arm_seq'), :url, :label, :strength, :isNondurable, :requiredPower, :requiredIntelligence, :requiredCourses) "
			+ "ON CONFLICT on constraint offence_arm_un DO UPDATE SET update_time = now(), row_version = offence_arm.row_version + 1, "
			+ "name = EXCLUDED.name, strength = EXCLUDED.strength, is_nondurable = EXCLUDED.is_nondurable, required_power = EXCLUDED.required_power, required_intelligence = EXCLUDED.required_intelligence, required_courses = EXCLUDED.required_courses "
			+ "where offence_arm.name != EXCLUDED.name OR offence_arm.strength != EXCLUDED.strength OR offence_arm.is_nondurable != EXCLUDED.is_nondurable OR offence_arm.required_power != EXCLUDED.required_power OR offence_arm.required_intelligence != EXCLUDED.required_intelligence OR offence_arm.required_courses != EXCLUDED.required_courses "
			+ "OR offence_arm.name is null and EXCLUDED.name is not null OR offence_arm.strength is null and EXCLUDED.strength is not null OR offence_arm.is_nondurable is null and EXCLUDED.is_nondurable is not null OR offence_arm.required_power is null and EXCLUDED.required_power is not null OR offence_arm.required_intelligence is null and EXCLUDED.required_intelligence is not null OR offence_arm.required_courses is null and EXCLUDED.required_courses is not null";

	public final static String UPSERT_DEFENCE_ARM_SQL = "INSERT INTO defence_arm (id, url, name, strength, is_nondurable, required_power, required_intelligence, required_courses) VALUES (nextval('defence_arm_seq'), :url, :label, :strength, :isNondurable, :requiredPower, :requiredIntelligence, :requiredCourses) "
			+ "ON CONFLICT on constraint defence_arm_un DO UPDATE SET update_time = now(), row_version = defence_arm.row_version + 1, "
			+ "name = EXCLUDED.name, strength = EXCLUDED.strength, is_nondurable = EXCLUDED.is_nondurable, required_power = EXCLUDED.required_power, required_intelligence = EXCLUDED.required_intelligence, required_courses = EXCLUDED.required_courses "
			+ "where defence_arm.name != EXCLUDED.name OR defence_arm.strength != EXCLUDED.strength OR defence_arm.is_nondurable != EXCLUDED.is_nondurable OR defence_arm.required_power != EXCLUDED.required_power OR defence_arm.required_intelligence != EXCLUDED.required_intelligence OR defence_arm.required_courses != EXCLUDED.required_courses "
			+ "OR defence_arm.name is null and EXCLUDED.name is not null OR defence_arm.strength is null and EXCLUDED.strength is not null OR defence_arm.is_nondurable is null and EXCLUDED.is_nondurable is not null OR defence_arm.required_power is null and EXCLUDED.required_power is not null OR defence_arm.required_intelligence is null and EXCLUDED.required_intelligence is not null OR defence_arm.required_courses is null and EXCLUDED.required_courses is not null";

	private static final Logger LOG = LoggerFactory.getLogger(ArmImportService.class);
	private static final String JOB_PARAM_KEY_UNIQUE_ID = "uuid";

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job importDefenceArmsJob;

	@Autowired
	private Job importOffenceArmsJob;

	@Scheduled(cron = "${wiki.categories.offence-arms.cron-schedule}")
	public void importOffenceArms() {
		LOG.info("Start sync offence arms");

		final JobParameters jobParameters = new JobParametersBuilder()
				.addString(JOB_PARAM_KEY_UNIQUE_ID, UUID.randomUUID().toString()).toJobParameters();
		try {
			jobLauncher.run(importOffenceArmsJob, jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			LOG.error("Failed to update abilities", e);
		}
	}

	@Scheduled(cron = "${wiki.categories.defence-arms.cron-schedule}")
	public void importDefenceArms() {
		LOG.info("Start sync defence arms");

		final JobParameters jobParameters = new JobParametersBuilder()
				.addString(JOB_PARAM_KEY_UNIQUE_ID, UUID.randomUUID().toString()).toJobParameters();
		try {
			jobLauncher.run(importDefenceArmsJob, jobParameters);

		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			LOG.error("Failed to update abilities", e);
		}
	}
}
