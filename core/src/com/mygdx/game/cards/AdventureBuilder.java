package com.mygdx.game.cards;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Need;
import com.mygdx.game.screens.GameMenu;

public class AdventureBuilder {
	
	public static AdventureCard getNewAdventure(Location location, final CardMechanics cardMechanics, final CharacterBank characterBank) {
		
		switch(location) {
			case FOREST:
				return forestArea(cardMechanics, characterBank);
			case TUTORIAL:
				return tutorialArea(cardMechanics, characterBank);
			default:
			break;
		}
		return null;
	}
	
	private static AdventureCard tutorialArea(final CardMechanics cardMechanics, final CharacterBank characterBank) {
		AdventureCard card = null;
		
		AdventureEffect reward = new AdventureEffect() {
			
			@Override
			public void act() {
				cardMechanics.getChosenCharacter().incrementNeed(Need.GOLD, 10);
				cardMechanics.getChosenCharacter().incrementNeed(Need.HEALTH, -10);
			}
		};
		
		AdventureEffect penalty = new AdventureEffect() {
			
			@Override
			public void act() {
			}
		};
		
		card = new AdventureCard("The Adventure Begins", "A new day dawns in the lands of Test-topia! The sun is shining, birds are chirping and orcs are marching to destroy the party! Choose someone to go forth and battle by clicking their portrait!",
								 new Choice("",
								 new AdventureCard("The Adventure Ends!", "The party member slays the orcs with relative ease and are given some money as a reward. They suffered some wounds however. Great job!", new Choice("Claim Reward", reward, penalty) {
			
			@Override
			public void choose() {
				this.getReward().act();
			}
			
		})));
		
		return card;
	}
	
	private static AdventureCard forestArea(final CardMechanics cardMechanics, final CharacterBank characterBank) {
		AdventureCard card = null;
		
		Choice choice1 = new Choice("Recruit the friend", new AdventureEffect() {
			
			@Override
			public void act() {
				if(characterBank.getCharacters().size() < 5)
					characterBank.addCharacter(new PartyCharacter());
			}
		}, new AdventureEffect() {
			
			@Override
			public void act() {
				if(characterBank.getCharacters().size() < 5)
					characterBank.addCharacter(new PartyCharacter());
			}
		}){
			@Override
			public void choose() {
				this.getReward().act();
			}
		};
		
		Choice choice2 = new Choice("Eat the friend", new AdventureEffect() {
			
			@Override
			public void act() {
				cardMechanics.getChosenCharacter().incrementNeed(Need.HUNGER, 40);
			}
		}, new AdventureEffect() {
			
			@Override
			public void act() {
				cardMechanics.getChosenCharacter().incrementNeed(Need.HUNGER, 40);
			}
		}) {
			@Override
			public void choose() {
				this.getReward().act();
			}
		};
		
		Choice choiceDescription = new Choice("", new AdventureCard("Recruiting a new friend", "You find someone in the woods. What will they do?", choice1, choice2));
		
		card = new AdventureCard("Recruiting a new friend", "From the woods you hear the sound of giggling, choose someone to venture forth!", choiceDescription);
		
		return card;
	}

}
