package com.mygdx.game.entities.ui;

import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.components.primitive.RemoveComponent;
import com.mygdx.game.components.ui.BarComponent;
import com.mygdx.game.components.ui.CharacterComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public abstract class CharacterStatBarEntity extends BarEntity {
	
	public CharacterStatBarEntity(int x, int y, int width, int height, int min, int max) {
		this.add(new UiPositionComponent(x, y));
		this.add(new BarComponent(width, height, min, max));
		this.add(new RemoveComponent());
	}
	
	public void setCharacter(PartyCharacter character) {
		this.add(new CharacterComponent(character));
	}
	
}
