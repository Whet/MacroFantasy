package com.mygdx.game.actor.traits;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum CharacterTrait {
	Berserker("The lower this character’s hit points, the greater the % boost to its combat stats will be. Berserkers cannot die from hunger, instead when their hunger is 0 they lose HP equivalent to their hunger loss until they are dead. Berserkers will NEVER run from combat."),
	SilverTongue("When this character uses this ability, he gives your party a 50% chance to charm monsters, vendors and others bypassing negative effects. If he fails… Things could get uglier."),
	Avarice("This character will leave the party if his gold dips below 150. Money traded to this character cannot be re-traded and is only retrievable from his/her corpse."),
	Ferocity("This character will survive for a single turn when reduced to below 0 HP. If a turn passes and this character isn’t healed above 0 he will die. This only works in Combat situations."),
	Regeneration("This character can heal his wounds much faster than normal both inside and outside of combat.");

	private final String description;
	
	CharacterTrait (String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	} 
	
	private static final List<CharacterTrait> VALUES =
		    Collections.unmodifiableList(Arrays.asList(values()));
	  private static final int SIZE = VALUES.size();
	  private static final Random RANDOM = new Random();

	  public static CharacterTrait randomTrait()  {
	    return VALUES.get(RANDOM.nextInt(SIZE));
	  }
}
