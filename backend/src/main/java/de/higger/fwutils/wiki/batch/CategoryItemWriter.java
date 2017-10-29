package de.higger.fwutils.wiki.batch;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.util.Assert;

import de.higger.fwutils.wiki.parse.item.ParseItem;

public class CategoryItemWriter implements ItemWriter<ParseItem> {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryItemWriter.class);

	private final DataSource dataSource;

	private final String upsertStatement;

	private JdbcBatchItemWriter<ParseItem> writer;

	public CategoryItemWriter(final DataSource dataSource, final String statement) {
		Assert.notNull(dataSource, "A DataSource is required.");
		Assert.notNull(statement, "A native statement is required to execute update.");

		this.dataSource = dataSource;
		this.upsertStatement = statement;
	}

	@BeforeStep
	public void prepareWriter() {

		LOG.debug("Prepare writer with statement {}", upsertStatement);

		final JdbcBatchItemWriter<ParseItem> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		writer.setSql(upsertStatement);
		writer.setDataSource(dataSource);
		writer.setAssertUpdates(false);
		writer.afterPropertiesSet();

		this.writer = writer;
	}

	@Override
	public void write(final List<? extends ParseItem> items) throws Exception {
		writer.write(items);
	}

}
