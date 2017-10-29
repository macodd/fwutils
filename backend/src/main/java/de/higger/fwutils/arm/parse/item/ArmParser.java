package de.higger.fwutils.arm.parse.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.higger.fwutils.wiki.exception.ItemParseException;
import de.higger.fwutils.wiki.parse.Hyperlink;
import de.higger.fwutils.wiki.parse.ItemParser;

public class ArmParser implements ItemParser<Arm> {

	private static final Logger LOG = LoggerFactory.getLogger(ArmParser.class);

	private static final String KEYWORD_NON_DURABLE_ITEM = "magische Item";
	private static final String SIGN_NON_DURABLE_ITEM = "(M)";

	private static final String REQUIRED_POWER_PATTERN = "(Mindeststärke:[^0-9]*)([0-9]+[.0-9]*)(.*)";
	private static final String REQUIRED_COURSES_PATTERN = "(Mindestakademielimit:[^0-9]*)([0-9]+[.0-9]*)(.*)";
	private static final String REQUIRED_INTELLIGENCE_PATTERN = "(Mindestintelligenz:[^0-9]*)([0-9]+[.0-9]*)(.*)";
	private static final String REQUIRED_RACE_PATTERN = "(Rasse: )(.*)";
	private static final String STRENGTH_PATTERN = "(.*Stärke:[^0-9]*)([0-9]+[.0-9]*)(.*)";

	private static final int MAX_STRENGTH = Integer.MAX_VALUE;

	private final ArmType armType;

	public ArmParser(final ArmType armType) {
		this.armType = armType;
	}

	@Override
	public Arm parseItem(final Hyperlink hyperlink, final Document doc) throws ItemParseException {

		final Arm arm = new Arm(hyperlink.getRelativeLocation(), armType);

		final Element weaponLayout = doc.select("div#mw-content-text").first();

		final String weaponName = doc.getElementById("firstHeading").text();
		arm.setLabel(weaponName);

		final boolean isNondurable = isNondurable(weaponLayout);
		arm.setIsNondurable(isNondurable);

		final Elements requirements = weaponLayout.select(".layout_requirements").first().getElementsByTag("li");

		if (requirements.size() > 0) {

			final int requiredPower = getRequiredPower(requirements);
			arm.setRequiredPower(requiredPower);

			final int requiredCourses = getRequiredCourses(requirements);
			arm.setRequiredCourses(requiredCourses);

			final int requiredIntelligence = getRequiredIntelligence(requirements);
			arm.setRequiredIntelligence(requiredIntelligence);

			final String requiredRace = getRequiredRace(requirements);
			arm.setRequiredRace(requiredRace);

		}

		final int minimumStrength = getMinimumStrength(weaponLayout);
		arm.setStrength(minimumStrength);

		if (arm.getStrength() == 0) {
			arm.setStrength(MAX_STRENGTH);
			LOG.warn("Failed to parse strength at {} on item {} ", armType.name(), arm.toString());
		}

		return arm;
	}

	private int getRequiredPower(final Elements requirements) {
		return getRequirementIntegerValue(REQUIRED_POWER_PATTERN, requirements);
	}

	private int getRequiredCourses(final Elements requirements) {
		return getRequirementIntegerValue(REQUIRED_COURSES_PATTERN, requirements);
	}

	private int getRequiredIntelligence(final Elements requirements) {

		return getRequirementIntegerValue(REQUIRED_INTELLIGENCE_PATTERN, requirements);
	}

	private String getRequiredRace(final Elements requirements) {

		final String requirementValue = getRequirementValue(REQUIRED_RACE_PATTERN, requirements);

		return requirementValue;
	}

	private boolean isNondurable(final Element weaponLayout) {

		boolean isNondurable = false;
		final Elements spans = weaponLayout.getElementsByTag("span");

		for (final Element span : spans) {
			final String title = span.attr("title");

			if (title.contains(KEYWORD_NON_DURABLE_ITEM) && SIGN_NON_DURABLE_ITEM.equals(span.text())) {
				isNondurable = true;
				break;
			}
		}
		return isNondurable;
	}

	private int getMinimumStrength(final Element weaponLayout) {

		int minimumStrength = 0;

		final Elements layouts = weaponLayout.select("div.layout_desc");

		outer: for (final Element layout : layouts) {

			final Elements cursiveTexts = layout.getElementsByTag("i");

			for (final Element element : cursiveTexts) {

				final String content = element.text();

				final Pattern p = Pattern.compile(STRENGTH_PATTERN);
				final Matcher m = p.matcher(content);

				if (m.matches()) {

					String requiredValueGroup = m.group(2);

					requiredValueGroup = requiredValueGroup.replaceAll("\\.", "");

					minimumStrength = Integer.valueOf(requiredValueGroup);

					break outer;
				}
			}
		}

		return minimumStrength;
	}

	private int getRequirementIntegerValue(final String pattern, final Elements requirements) {
		final String requiredValueResult = getRequirementValue(pattern, requirements);

		int result = 0;

		if (requiredValueResult != null) {
			result = Integer.parseInt(requiredValueResult);
		}

		return result;
	}

	private String getRequirementValue(final String pattern, final Elements requirements) {

		for (final Element requirement : requirements) {

			final String requirementText = requirement.text();

			final Pattern p = Pattern.compile(pattern);
			final Matcher m = p.matcher(requirementText);

			if (m.matches()) {
				String requiredValueGroup = m.group(2);
				requiredValueGroup = requiredValueGroup.replaceAll("\\.", "");

				return requiredValueGroup;
			}
		}

		return null;
	}

}
