package de.higger.fwutils.arm.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.higger.fwutils.arm.parse.item.ArmParser;
import de.higger.fwutils.arm.parse.item.ArmType;
import de.higger.fwutils.arm.service.ArmImportService;
import de.higger.fwutils.wiki.batch.CategoryItemProcessor;
import de.higger.fwutils.wiki.batch.CategoryItemReader;
import de.higger.fwutils.wiki.batch.CategoryItemWriter;
import de.higger.fwutils.wiki.batch.CategorySkipListener;
import de.higger.fwutils.wiki.config.Category;
import de.higger.fwutils.wiki.config.WikiConfiguration;
import de.higger.fwutils.wiki.parse.CategoryParser;
import de.higger.fwutils.wiki.parse.Hyperlink;
import de.higger.fwutils.wiki.parse.ParseItem;

@Configuration
@EnableBatchProcessing
public class ImportOffenceArmBatchConfig {

	private static final String JOB_NAME = "import-offence-arms-job";
	private static final String STEP_NAME = "import-offence-arms-step";

	@Autowired
	private CategoryParser categoryParser;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	@StepScope
	public CategoryItemReader offenceArmsItemReader(final WikiConfiguration wikiConfiguration) {

		return new CategoryItemReader(categoryParser, wikiConfiguration.getCategories().getOffenceArms());
	}

	@Bean
	@StepScope
	public CategoryItemProcessor offenceArmsItemProcessor() {
		return new CategoryItemProcessor(categoryParser, new ArmParser(ArmType.DEFENCE));
	}

	@Bean
	@StepScope
	public CategoryItemWriter offenceArmsItemWriter() {

		return new CategoryItemWriter(dataSource, ArmImportService.UPSERT_DEFENCE_ARM_SQL);
	}

	@Bean
	public Job importOffenceArmsJob(final Step importOffenceArmsStep) {
		return this.jobBuilderFactory.get(JOB_NAME) //
				.incrementer(new RunIdIncrementer()) //
				.flow(importOffenceArmsStep) //
				.end() //
				.build();
	}

	@Bean
	public Step importOffenceArmsStep(final WikiConfiguration wikiConfiguration) {

		final Category category = wikiConfiguration.getCategories().getOffenceArms();

		return this.stepBuilderFactory.get(STEP_NAME) //
				.<Hyperlink, ParseItem> chunk(1).faultTolerant().skip(Exception.class)
				.skipLimit(category.getSkipLimit()).listener(new CategorySkipListener()) //
				.reader(offenceArmsItemReader(null)) //
				.processor(offenceArmsItemProcessor()) //
				.writer(offenceArmsItemWriter()) //
				.build();
	}

}
