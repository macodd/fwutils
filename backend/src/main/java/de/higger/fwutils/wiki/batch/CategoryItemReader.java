package de.higger.fwutils.wiki.batch;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.util.Assert;

import de.higger.fwutils.wiki.config.Category;
import de.higger.fwutils.wiki.parse.CategoryParser;
import de.higger.fwutils.wiki.parse.Hyperlink;

public class CategoryItemReader implements ItemReader<Hyperlink> {

	private final Category category;
	private final CategoryParser categoryParser;

	private Iterator<Hyperlink> itemsOfCategoryIterator;

	public CategoryItemReader(final CategoryParser categoryParser, final Category category) {
		Assert.notNull(categoryParser, "A CategoryParser is required.");
		Assert.notNull(category, "A Category is required.");

		this.categoryParser = categoryParser;
		this.category = category;
	}

	@BeforeStep
	public void prepareItemReader() {
		final List<Hyperlink> itemsOfCategory = categoryParser.getItemsOfCategory(category.getLocation(),
				h -> category.getExclusions().contains(h.getRelativeLocation()) == false);

		this.itemsOfCategoryIterator = itemsOfCategory.iterator();
	}

	@Override
	public Hyperlink read() {
		if (itemsOfCategoryIterator.hasNext()) {
			return itemsOfCategoryIterator.next();
		}
		return null;
	}

}
