package de.higger.fwutils.ability.parse.item;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import de.higger.fwutils.wiki.parse.Hyperlink;
import de.higger.fwutils.wiki.parse.ItemParser;

public class AbilityParser implements ItemParser<AbilityItem> {

	private static final String ABILITY_NAME_LERNTECHNIK = "Lerntechnik";
	private static final String ID_BASE_LERNTIME = "CFcalc";
	private static final String ID_MAX_LEVEL = "CFmax";

	@Override
	public AbilityItem parseItem(final Hyperlink hyperlink, final Document document) {

		final AbilityItem abilityItem = new AbilityItem(hyperlink.getRelativeLocation());

		final String abilityName = document.getElementById("firstHeading").text();
		abilityItem.setLabel(abilityName);

		if (ABILITY_NAME_LERNTECHNIK.equals(abilityName)) {
			abilityItem.setBaseTime(35000);

			abilityItem.setMaxLevel((short) 50);
		} else {

			final Element elementBaseLerntime = document.getElementById(ID_BASE_LERNTIME);
			final String baseTime = elementBaseLerntime.text();
			abilityItem.setBaseTime(Integer.valueOf(baseTime));

			final Element elementMaxLevel = document.getElementById(ID_MAX_LEVEL);
			final String maxLevel = elementMaxLevel.text();
			abilityItem.setMaxLevel(Short.valueOf(maxLevel));
		}

		return abilityItem;
	}

}
