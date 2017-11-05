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
public class ImportDefenceArmBatchConfig {

	private static final String JOB_NAME = "import-defence-arms-job";
	private static final String STEP_NAME = "import-defence-arms-step";

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
	public CategoryItemReader defenceArmsItemReader(final WikiConfiguration wikiConfiguration) {

		return new CategoryItemReader(categoryParser, wikiConfiguration.getCategories().getDefenceArms());
	}

	@Bean
	@StepScope
	public CategoryItemProcessor defenceArmsItemProcessor() {
		return new CategoryItemProcessor(categoryParser, new ArmParser(ArmType.DEFENCE));
	}

	@Bean
	@StepScope
	public CategoryItemWriter defenceArmsItemWriter() {

		return new CategoryItemWriter(dataSource, ArmImportService.UPSERT_DEFENCE_ARM_SQL);
	}

	@Bean
	public Job importDefenceArmsJob(final Step importDefenceArmsStep) {
		return this.jobBuilderFactory.get(JOB_NAME) //
				.incrementer(new RunIdIncrementer()) //
				.flow(importDefenceArmsStep) //
				.end() //
				.build();
	}

	@Bean
	public Step importDefenceArmsStep(final WikiConfiguration wikiConfiguration) {

		final Category category = wikiConfiguration.getCategories().getDefenceArms();

		return this.stepBuilderFactory.get(STEP_NAME) //
				.<Hyperlink, ParseItem> chunk(1).faultTolerant().skip(Exception.class)
				.skipLimit(category.getSkipLimit()).listener(new CategorySkipListener()) //
				.reader(defenceArmsItemReader(null)) //
				.processor(defenceArmsItemProcessor()) //
				.writer(defenceArmsItemWriter()) //
				.build();
	}

}
