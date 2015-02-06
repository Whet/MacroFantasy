package com.mygdx.game.actor.traits;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum CareerTrait {
	DrainSoul("This character can drain 90% of a friendly character’s stats and add them to his own (this also increases their max caps). This can also be used on certain enemies but with a 30% stat yield. Friendly targets that are Souldrained will have their happiness reduced massively."),
	LifeTap("Turns HP into Mana for the dark-caster only"),
	Cleanse("Purges diseases and curses."),
	Crusader("Combat proficiency is based upon the character’s maximum hit points.");
	

	private final String description;
	
	CareerTrait (String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	} 
	
	private static final List<CareerTrait> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	  private static final int SIZE = VALUES.size();
	  private static final Random RANDOM = new Random();

	  public static CareerTrait randomTrait()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	  }
}
