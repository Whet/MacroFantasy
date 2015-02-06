package com.mygdx.game.entities.ui;

import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.primitive.MultiRegionComponent;
import com.mygdx.game.components.primitive.RemoveComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public class UiMultiButtonEntity extends Entity {

	public UiMultiButtonEntity(int x, int y, List<TextureRegion> region) {
		this.add(new UiPositionComponent(x, y));
		this.add(new MultiRegionComponent(region));
		this.add(new UiMouseActivityComponent());
		this.add(new TextComponent(""));
		this.add(new RemoveComponent());
	}
	
	public UiMultiButtonEntity(int x, int y, int z, List<TextureRegion> region) {
		this.add(new UiPositionComponent(x, y, z));
		this.add(new MultiRegionComponent(region));
		this.add(new UiMouseActivityComponent());
		this.add(new TextComponent(""));
		this.add(new RemoveComponent());
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
