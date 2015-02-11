package com.mygdx.game.cards;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Job;
import com.mygdx.game.actor.enums.Need;
import com.mygdx.game.screens.GameMenu;

public class CardMechanics {

	private AdventureCard chosenCard;
	private GameMenu gameMenu;
	private CharacterBank characterBank;
	private PartyCharacter chosenCharacter;
	
	public CardMechanics(GameMenu gameMenu) {
		this.gameMenu = gameMenu;
		this.characterBank = CharacterBank.getInstance();
	}
	
	public void chooseCharacter(PartyCharacter chosenCharacter) {
		if(this.chosenCard != null && this.chosenCharacter == null && chosenCharacter.isAlive()) {
			this.chosenCharacter = chosenCharacter;
			this.chosenCard = chosenCard.next();
			gameMenu.showChosenCard();
		}
		else if(this.chosenCard == null && chosenCharacter.isAlive()) {
			gameMenu.showStatMenu(chosenCharacter);
		}
	}
	
	public void chooseCard(AdventureCard chosenCard) {
		this.chosenCard = chosenCard;
	}

	public AdventureCard getChosenCard() {
		return chosenCard;
	}

	public PartyCharacter getChosenCharacter() {
		return chosenCharacter;
	}

	public void makeChoice(Choice choice) {
		if(choice.getNextCard() != null) {
			this.chosenCard = chosenCard.next();
			gameMenu.showChosenCard();
		}
		else {
			// End quest line
			choice.choose();

			this.chosenCard = null;
			this.chosenCharacter = null;
			endTurn();
		}
	}

	public void endTurn() {
		gameMenu.hideCards();
		gameMenu.updateCharacters();
		
		// Work out everyone's job effects
		int healthBoost = 0;
		int manaBoost = 0;
		int foodBoost = 0;
		int happyBoost = 0;
		int moneyBoost = 0;
		
		for (PartyCharacter character : characterBank.getCharacters()) {
			if(character.isAlive()) {
				// Drain stats based on consumption rates
				character.endTurn();
				
				if(character.getJob() != null)
				switch(character.getJob()) {
					case ALCHEMIST:
						manaBoost = Math.round(character.getSkill(Job.ALCHEMIST)/10);
						character.incrementSkill(Job.ALCHEMIST, 1);
					break;
					case BARD:
						happyBoost = Math.round(character.getSkill(Job.BARD)/10);
						character.incrementSkill(Job.BARD, 1);
					break;
					case COOK:
						foodBoost = Math.round(character.getSkill(Job.COOK)/10);
						character.incrementSkill(Job.COOK, 1);
					break;
					case HEALER:
						healthBoost = Math.round(character.getSkill(Job.HEALER)/10);
						character.incrementSkill(Job.HEALER, 1);
					break;
					case MERCHANT:
						moneyBoost = Math.round(character.getSkill(Job.MERCHANT)/10);
						character.incrementSkill(Job.MERCHANT, 1);
					break;
					default:
					break;
				}
			}
			else
				System.out.println(character.getName() + " is not here anymore due to " + character.getCauseOfDeath());
		}
		
		// Go through all characters and give the bonuses
		for (PartyCharacter character : characterBank.getCharacters()) {
			if(character.isAlive()) {
				character.incrementNeed(Need.HEALTH, healthBoost);
				character.incrementNeed(Need.MANA, manaBoost);
				character.incrementNeed(Need.HUNGER, foodBoost);
				character.incrementNeed(Need.HAPPINESS, happyBoost);
				character.incrementNeed(Need.GOLD, moneyBoost);
			}
		}
		
		// Update for characters dying and showing leaving message
		gameMenu.updateCharacters();
	}
	
}
