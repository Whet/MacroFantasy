package com.mygdx.game.cards;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.PartyCharacter;
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
		if(this.chosenCharacter == null) {
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
			gameMenu.hideCards();
		}
	}
	
}
