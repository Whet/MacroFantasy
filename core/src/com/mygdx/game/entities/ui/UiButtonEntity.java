package com.mygdx.game.entities.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.primitive.TextureComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public class UiButtonEntity extends Entity {

	public UiButtonEntity(int x, int y, TextureRegion region) {
		this.add(new UiPositionComponent(x, y));
		this.add(new TextureComponent(region));
		this.add(new UiMouseActivityComponent());
	}
	
	public UiButtonEntity(int x, int y, int z, TextureRegion region) {
		this.add(new UiPositionComponent(x, y, z));
		this.add(new TextureComponent(region));
		this.add(new UiMouseActivityComponent());
	}
	
	public boolean mD(int x, int y) {
		return false;
	}
	
	public boolean mU(int x, int y) {
		return false;
	}
	
	public void mI(int x, int y) {
		
	}
	
	public void mO(int x, int y) {
		
	}
	
}
