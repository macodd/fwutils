package de.higger.fwutils.npc.batch;

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

import de.higger.fwutils.npc.parse.item.NPCParser;
import de.higger.fwutils.npc.service.NPCImportService;
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
public class ImportNPCBatchConfig {

	private static final String JOB_NAME = "import-npcs-job";
	private static final String STEP_NAME = "import-npcs-step";

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
	public CategoryItemReader npcsItemReader(final WikiConfiguration wikiConfiguration) {

		return new CategoryItemReader(categoryParser, wikiConfiguration.getCategories().getNpcs());
	}

	@Bean
	@StepScope
	public CategoryItemProcessor npcsItemProcessor() {
		return new CategoryItemProcessor(categoryParser, new NPCParser());
	}

	@Bean
	@StepScope
	public CategoryItemWriter npcsItemWriter() {

		return new CategoryItemWriter(dataSource, NPCImportService.UPSERT_NPC_SQL);
	}

	@Bean
	public Job importNpcsJob(final Step importNpcsStep) {
		return this.jobBuilderFactory.get(JOB_NAME) //
				.incrementer(new RunIdIncrementer()) //
				.flow(importNpcsStep) //
				.end() //
				.build();
	}

	@Bean
	public Step importNpcsStep(final WikiConfiguration wikiConfiguration) {

		final Category category = wikiConfiguration.getCategories().getNpcs();

		return this.stepBuilderFactory.get(STEP_NAME) //
				.<Hyperlink, ParseItem> chunk(1).faultTolerant().skip(Exception.class)
				.skipLimit(category.getSkipLimit()).listener(new CategorySkipListener()) //
				.reader(npcsItemReader(null)) //
				.processor(npcsItemProcessor()) //
				.writer(npcsItemWriter()) //
				.build();
	}

}
