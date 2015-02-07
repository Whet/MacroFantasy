package com.mygdx.game.cards;

import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.CharacterBank;
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
		this.chosenCharacter = chosenCharacter;
		gameMenu.hideCards();
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
	
}
