package com.mygdx.game.entities.ui;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.primitive.RemoveComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public class TextEntity extends Entity {

	public TextEntity() {
		this.add(new TextComponent(""));
		this.add(new UiPositionComponent(0, 0));
		this.add(new RemoveComponent());
	}
	
}
