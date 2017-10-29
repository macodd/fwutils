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

import de.higger.fwutils.wiki.batch.ImportBatchConfiguration;
import de.higger.fwutils.wiki.config.Category;
import de.higger.fwutils.wiki.parse.ItemParser;
import de.higger.fwutils.wiki.parse.ParseItem;

@Service
public class WikiImportService {

	private static final Logger LOG = LoggerFactory.getLogger(WikiImportService.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private ImportBatchConfiguration importBatchConfiguration;

	public void importCategory(final String jobNameExtension, final ItemParser<? extends ParseItem> itemParser,
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
