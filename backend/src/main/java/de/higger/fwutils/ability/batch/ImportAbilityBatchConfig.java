package de.higger.fwutils.ability.batch;

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

import de.higger.fwutils.ability.parse.item.AbilityParser;
import de.higger.fwutils.ability.service.AbilityImportService;
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
public class ImportAbilityBatchConfig {

	private static final String JOB_NAME = "import-abilities-job";
	private static final String STEP_NAME = "import-abilities-step";

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
	public CategoryItemReader abilityItemReader(final WikiConfiguration wikiConfiguration) {

		return new CategoryItemReader(categoryParser, wikiConfiguration.getCategories().getAbilities());
	}

	@Bean
	@StepScope
	public CategoryItemProcessor abilityItemProcessor() {
		return new CategoryItemProcessor(categoryParser, new AbilityParser());
	}

	@Bean
	@StepScope
	public CategoryItemWriter abilityItemWriter() {

		return new CategoryItemWriter(dataSource, AbilityImportService.UPSERT_ABILITY_SQL);
	}

	@Bean
	public Job importAbilitiesJob(final Step importAbilitiesStep) {
		return this.jobBuilderFactory.get(JOB_NAME) //
				.incrementer(new RunIdIncrementer()) //
				.flow(importAbilitiesStep) //
				.end() //
				.build();
	}

	@Bean
	public Step importAbilitiesStep(final WikiConfiguration wikiConfiguration) {

		final Category category = wikiConfiguration.getCategories().getAbilities();

		return this.stepBuilderFactory.get(STEP_NAME) //
				.<Hyperlink, ParseItem> chunk(1).faultTolerant().skip(Exception.class)
				.skipLimit(category.getSkipLimit()).listener(new CategorySkipListener()) //
				.reader(abilityItemReader(null)) //
				.processor(abilityItemProcessor()) //
				.writer(abilityItemWriter()) //
				.build();
	}

}
