package com.mygdx.game.cards;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.enums.CharacterValues.Stat;

public class AdventureBuilder {

	public static AdventureCard getNewAdventure(final CardMechanics cardMechanics, final CharacterBank characterBank) {
		
		AdventureCard card = null;
		
		AdventureEffect reward = new AdventureEffect() {
			
			@Override
			public void act() {
				cardMechanics.getChosenCharacter().incrementStat(Stat.GOLD, 10);
			}
		};
		
		AdventureEffect penalty = new AdventureEffect() {
			
			@Override
			public void act() {
			}
		};
		
		card = new AdventureCard("Test 1", "Test 2", new Choice("", new AdventureCard("Test 3", "Test 4", new Choice("Finish", reward, penalty) {
			
			@Override
			public void choose() {
				this.getReward().act();
			}
			
		})));
		
		return card;
	}

}
