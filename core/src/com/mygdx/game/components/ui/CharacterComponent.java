package com.mygdx.game.components.ui;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.actor.Character;

public class CharacterComponent extends Component {

	public Character character;

	public CharacterComponent(Character character) {
		this.character = character;
	}
	
}
