package com.mygdx.game.entities.ui;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.primitive.RemoveComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public class TextButtonEntity extends Entity {

	public TextButtonEntity() {
		this.add(new TextComponent(""));
		this.add(new UiPositionComponent(0, 0));
		this.add(new UiMouseActivityComponent());
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
