package com.mygdx.game.entities.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.primitive.TextureComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public class UiImageEntity extends Entity {

	public UiImageEntity(int x, int y, TextureRegion region) {
		this.add(new UiPositionComponent(x, y));
		this.add(new TextureComponent(region));
	}
	
	public UiImageEntity(int x, int y, int z, TextureRegion region) {
		this.add(new UiPositionComponent(x, y, z));
		this.add(new TextureComponent(region));
	}
}
