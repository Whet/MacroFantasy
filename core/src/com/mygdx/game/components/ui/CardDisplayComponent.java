package com.mygdx.game.components.ui;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.cards.AdventureCard;

public class CardDisplayComponent extends Component {

	public AdventureCard card;
	
	public CardDisplayComponent(AdventureCard card) {
		this.card = card;
	}
	
}
