package com.mygdx.game.actor.enums;


public class CharacterTraits {
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
	}
	
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
	}
	
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
	}
}
