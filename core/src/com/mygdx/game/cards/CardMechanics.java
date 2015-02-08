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
	
	public CardMechanics(GameMenu gameMenu, CharacterBank characterBank) {
		this.gameMenu = gameMenu;
		this.characterBank = characterBank;
	}
	
	public void chooseCharacter(PartyCharacter chosenCharacter) {
		if(this.chosenCard != null && this.chosenCharacter == null && chosenCharacter.isAlive()) {
			this.chosenCharacter = chosenCharacter;
			this.chosenCard = chosenCard.next();
			gameMenu.showChosenCard();
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
		
		for (PartyCharacter character:characterBank.characters) {
			// Drain stats based on consumption rates
			character.endTurn();
			
			if(character.getJob() != null)
			switch(character.getJob()) {
				case Alchemist:
					manaBoost = character.getSkill(Job.Alchemist);
				break;
				case Bard:
					happyBoost = character.getSkill(Job.Bard);
				break;
				case Cook:
					foodBoost = character.getSkill(Job.Cook);
				break;
				case Healer:
					healthBoost = character.getSkill(Job.Healer);
				break;
				case Merchant:
					moneyBoost = character.getSkill(Job.Merchant);
				break;
				default:
				break;
			}
		}
		
		// Go through all characters and give the bonuses
		for (PartyCharacter character:characterBank.characters) {
			character.incrementNeed(Need.HEALTH, healthBoost);
			character.incrementNeed(Need.MANA, manaBoost);
			character.incrementNeed(Need.HUNGER, foodBoost);
			character.incrementNeed(Need.HAPPINESS, happyBoost);
			character.incrementNeed(Need.GOLD, moneyBoost);
		}
	}
	
}
