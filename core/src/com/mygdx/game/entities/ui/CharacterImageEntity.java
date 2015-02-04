package com.mygdx.game.entities.ui;

import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.primitive.MultiRegionComponent;
import com.mygdx.game.components.primitive.RemoveComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public class CharacterImageEntity extends Entity {

	public CharacterImageEntity(int x, int y, List<TextureRegion> region) {
		this.add(new UiPositionComponent(x, y));
		this.add(new MultiRegionComponent(region));
		this.add(new TextComponent(""));
		this.add(new RemoveComponent());
	}
	
}
