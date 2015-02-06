package com.mygdx.game.actor.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CharacterValues {
	public enum Stat {
		HEALTH, MAXHEALTH, HUNGER, MAXHUNGER, GOLD, MAXGOLD, MANA, MAXMANA, HAPPINESS, MAXHAPPINESS;
	}

	public enum Race {
		ORC, HUMAN, ELF, HALFLING, DWARF, GNOME;
	}
	
	public enum Job {
		Healer("The healer heals the party every turn."),
		Alchemist("The alchemist regenerates the party's mana."),
		Cook("The cook feeds the party every turn."),
		Bard("The bard raises the party's happiness every turn."),
		Merchant("The merchant makes the party money every turn.");
		

		private final String description;
		
		Job (String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		} 
		
		private static final List<Job> VALUES =
			    Collections.unmodifiableList(Arrays.asList(values()));
		  private static final int SIZE = VALUES.size();
		  private static final Random RANDOM = new Random();

		  public static Job randomJob()  {
		    return VALUES.get(RANDOM.nextInt(SIZE));
		  }
	}
	
	public enum CauseOfDeath {
		HEALTH, HUNGER, GOLD
	}
}
