package com.mygdx.game.actor.values;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum GeneralTrait {
	Lustfallen("Plagued by lust, this character has a 50% chance to become useless in situations concerning the opposite sex."),
	LoneMaiden("This character’s happiness will deteriorate unless she is the only female in the party."),
	LastOfMyKind("This character’s stats improve when he/she is the only one of his kind in the party, be it the only male, the only troll, or the only black/white person. This trait can vary between characters but will always appear as ‘Last of My Kind’."),
	Nocturn("This character’s stats improve when it is Nighttime or in places absent of light.."),
	Pyrophobia("This character has a severe aversion to fire, if exposed to flame the character’s happiness will plummet, they will flee from combat and may even flee the party."),
	Drunkard("This character will raise the happiness of the entire party as much as a bard would, however he requires twice as much food and is a bit of a liability. In combat he has natural % evasion that is always the same as his % chance to miss.");
	

	private final String description;
	
	GeneralTrait (String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	} 
	
	private static final List<GeneralTrait> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	  private static final int SIZE = VALUES.size();
	  private static final Random RANDOM = new Random();

	  public static GeneralTrait randomTrait()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	  }
}
