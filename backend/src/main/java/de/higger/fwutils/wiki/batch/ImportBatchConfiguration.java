package de.higger.fwutils.wiki.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import de.higger.fwutils.wiki.config.Category;
import de.higger.fwutils.wiki.parse.CategoryParser;
import de.higger.fwutils.wiki.parse.Hyperlink;
import de.higger.fwutils.wiki.parse.ItemParser;
import de.higger.fwutils.wiki.parse.ParseItem;

@Configuration
@EnableBatchProcessing
public class ImportBatchConfiguration {

	private static final String STEP_NAME_PATTERN = "import-category-%s";
	private static final String JOB_NAME_PATTERN = "wiki-import-%s";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CategoryParser categoryParser;

	public Job createJob(final String jobNameExtension, final ItemParser<? extends ParseItem> itemParser,
			final Category category, final String statement) {
		Assert.notNull(jobNameExtension, "A jobNameExtension is required.");
		Assert.notNull(itemParser, "A ItemParser is required.");
		Assert.notNull(category, "A Category is required.");
		Assert.notNull(statement, "A native statement is required to execute update.");

		// @formatter:off
		final Step step = stepBuilderFactory
				.get(String.format(STEP_NAME_PATTERN, jobNameExtension)).<Hyperlink, ParseItem> chunk(1)
				.faultTolerant().skip(Exception.class).skipLimit(category.getSkipLimit())
				.listener(new CategorySkipListener())
				.reader(new CategoryItemReader(this.categoryParser, category))
				.processor(new CategoryItemProcessor(this.categoryParser, itemParser))
				.writer(new CategoryItemWriter(dataSource, statement))
				.build();
		
		final Job build = jobBuilderFactory
				.get(String.format(JOB_NAME_PATTERN, jobNameExtension))
				.incrementer(new RunIdIncrementer())
				.flow(step)
				.end().build();
		// @formatter:on
		return build;
	}
}
