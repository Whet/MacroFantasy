package com.mygdx.game.actor.enums;

public enum Job {
	HEALER("The healer heals the party every turn."),
	ALCHEMIST("The alchemist regenerates the party's mana."),
	COOK("The cook feeds the party every turn."),
	BARD("The bard raises the party's happiness every turn."),
	MERCHANT("The merchant makes the party money every turn.");
	

	private final String description;
	
	Job (String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	} 
}
