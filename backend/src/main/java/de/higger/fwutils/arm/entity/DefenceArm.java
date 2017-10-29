package de.higger.fwutils.arm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "defence_arm")
public class DefenceArm {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_defence_arm_seq")
	@SequenceGenerator(name = "id_defence_arm_seq", sequenceName = "defence_arm_seq")
	private long id;
	private String name;
	private String url;

	private Integer strength;
	private Boolean isNondurable;
	private Integer requiredPower;
	private Integer requiredIntelligence;
	private Integer requiredCourses;

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public Integer getStrength() {
		return strength;
	}

	public Boolean getIsNondurable() {
		return isNondurable;
	}

	public Integer getRequiredPower() {
		return requiredPower;
	}

	public Integer getRequiredIntelligence() {
		return requiredIntelligence;
	}

	public Integer getRequiredCourses() {
		return requiredCourses;
	}

}
