package de.higger.fwutils.arm.parse.item;

import de.higger.fwutils.wiki.parse.ParseItem;

public class Arm extends ParseItem {

	private static final long serialVersionUID = -6140338063523970449L;

	private Integer strength;
	private Boolean isNondurable;
	private Integer requiredPower;
	private Integer requiredIntelligence;
	private Integer requiredCourses;
	private String requiredRace;
	private final ArmType armType;

	public Arm(final String url, final ArmType armType) {
		super(url);
		this.armType = armType;
	}

	public Integer getStrength() {
		return strength;
	}

	public void setStrength(final Integer strength) {
		this.strength = strength;
	}

	public Boolean getIsNondurable() {
		return isNondurable;
	}

	public void setIsNondurable(final Boolean isNondurable) {
		this.isNondurable = isNondurable;
	}

	public Integer getRequiredPower() {
		return requiredPower;
	}

	public void setRequiredPower(final Integer requiredPower) {
		this.requiredPower = requiredPower;
	}

	public Integer getRequiredIntelligence() {
		return requiredIntelligence;
	}

	public void setRequiredIntelligence(final Integer requiredIntelligence) {
		this.requiredIntelligence = requiredIntelligence;
	}

	public Integer getRequiredCourses() {
		return requiredCourses;
	}

	public void setRequiredCourses(final Integer requiredCourses) {
		this.requiredCourses = requiredCourses;
	}

	public String getRequiredRace() {
		return requiredRace;
	}

	public void setRequiredRace(final String requiredRace) {
		this.requiredRace = requiredRace;
	}

	public ArmType getArmType() {
		return armType;
	}

}
