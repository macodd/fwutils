package de.higger.fwutils.wiki.parse.item;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.higger.fwutils.wiki.exception.ItemParseException;
import de.higger.fwutils.wiki.parse.Hyperlink;

public class NPCParser implements ItemParser<NPC> {

	private static final Logger LOG = LoggerFactory.getLogger(NPCParser.class);

	private final Pattern intervalPattern = Pattern.compile("([^0-9]*)([\\.0-9]+[ -]*[\\.0-9]*)([^0-9]*)");

	@Override
	public NPC parseItem(final Hyperlink hyperlink, final Document doc) throws ItemParseException {

		final NPC npc = new NPC(hyperlink.getRelativeLocation());

		final Element npcLayout = doc.select("div#mw-content-text").first();

		final Element npcDesc = npcLayout.select("div.layout_desc").first();
		if (npcDesc == null) {
			new ItemParseException(NPC.class.getSimpleName(), hyperlink);
		}

		final Elements details = npcDesc.select("p");

		final Element nameElement = details.first();
		final String name = nameElement.select("b").text();
		final String type = nameElement.select("a").text();

		npc.setLabel(name);
		npc.setNpcType(type);

		final Element strengthDetails = details.last();
		final Matcher strengthMatcher = intervalPattern.matcher(strengthDetails.html());

		if (strengthMatcher.find()) {
			final String minStrength = getLowerValue(strengthMatcher.group(2));
			final String maxStrength = getUpperValue(strengthMatcher.group(2));

			if (minStrength != null && !minStrength.equals("")) {
				npc.setMinStrength(Integer.parseInt(minStrength));
			}

			if (maxStrength != null && !maxStrength.equals("")) {
				npc.setMaxStrength(Integer.parseInt(maxStrength));
			}
		} else {
			npc.setMinStrength(0);
			npc.setMaxStrength(Integer.MAX_VALUE);
			LOG.warn("Failed to parse strength at NPC on item {} ", npc.toString());
		}

		final Element moneyDesc = npcLayout.select("div.layout_money p").first();
		if (moneyDesc != null && moneyDesc.textNodes().size() > 0) {
			final String money = moneyDesc.textNodes().get(0).text();
			final Matcher moneyMatcher = intervalPattern.matcher(money);

			if (moneyMatcher.find()) {
				final String moneyVal = getLowerValue(moneyMatcher.group(2));

				npc.setDroppedMoney(Integer.parseInt(moneyVal));
			} else {
				npc.setDroppedMoney(0);
			}
		}

		final Element lpDesc = npcLayout.select("div.layout_lp p").first();
		if (lpDesc.textNodes().size() > 0) {
			final String health = lpDesc.textNodes().get(0).text();
			final Matcher healthMatcher = intervalPattern.matcher(health);

			if (healthMatcher.find()) {
				final String minHealth = getLowerValue(healthMatcher.group(2));
				final String maxHealth = getUpperValue(healthMatcher.group(2));

				npc.setMinHealth(Integer.parseInt(minHealth));
				npc.setMaxHealth(Integer.parseInt(maxHealth));
			} else {
				npc.setMinHealth(0);
				npc.setMaxHealth(Integer.MAX_VALUE);
				LOG.warn("Failed to parse health at NPC on item {} ", npc.toString());
			}
		}

		return npc;
	}

	public static String getLowerValue(final String requValue) {
		return getSplittedValue(requValue, (byte) 0);
	}

	public static String getUpperValue(final String requValue) {
		return getSplittedValue(requValue, (byte) 1);
	}

	public static String getSplittedValue(String requValue, final byte pos) {

		requValue = requValue.replaceAll("\\.", "");
		requValue = requValue.replaceAll(" ", "");

		final String[] spl = requValue.split("-");
		final byte maxPos = (byte) (spl.length - 1);

		requValue = requValue.split("-")[pos > maxPos ? maxPos : pos];
		return requValue;
	}

}
