package com.mygdx.game.entities.ui;

import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.ui.CharacterComponent;
import com.mygdx.game.utils.WordUtils;

public class CharacterTextEntity extends TextEntity {

	public CharacterTextEntity() {
		
	}
	
	public void setCharacter(PartyCharacter character) {
		this.add(new CharacterComponent(character));
		
		if(character.getJob() != null)
			this.getComponent(TextComponent.class).text = WordUtils.capitalise(character.getJob().toString()) + " " + character.getName();
		else
			this.getComponent(TextComponent.class).text = character.getName() + " the Unemployed";
	}
	
}
