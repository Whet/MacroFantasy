package com.mygdx.game.components.ui;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.actor.PartyCharacter;

public class CharacterComponent extends Component {

	public PartyCharacter character;

	public CharacterComponent(PartyCharacter character) {
		this.character = character;
	}
	
}
