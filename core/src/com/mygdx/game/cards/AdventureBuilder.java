package com.mygdx.game.cards;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.enums.Need;

public class AdventureBuilder {

	public static AdventureCard getNewAdventure(final CardMechanics cardMechanics, final CharacterBank characterBank) {
		
		AdventureCard card = null;
		
		AdventureEffect reward = new AdventureEffect() {
			
			@Override
			public void act() {
				cardMechanics.getChosenCharacter().incrementNeed(Need.GOLD, 10);
			}
		};
		
		AdventureEffect penalty = new AdventureEffect() {
			
			@Override
			public void act() {
			}
		};
		
		card = new AdventureCard("The Adventure Begins", "A new day dawns in the lands of Test-topia! The sun is shining, birds are chirping and orcs are marching to destroy the party! Choose someone to go forth and battle by clicking their portrait!",
								 new Choice("",
								 new AdventureCard("The Adventure Ends!", "The party member says the orcs with ease and are given some money as a reward. Great job!", new Choice("Claim Reward", reward, penalty) {
			
			@Override
			public void choose() {
				this.getReward().act();
			}
			
		})));
		
		return card;
	}

}
