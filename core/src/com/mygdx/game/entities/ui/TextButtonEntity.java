package com.mygdx.game.entities.ui;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.primitive.RemoveComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.ui.UiButtonDownComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public class TextButtonEntity extends Entity {

	public TextButtonEntity() {
		this.add(new TextComponent(""));
		this.add(new UiPositionComponent(0, 0));
		this.add(new UiButtonDownComponent(false));
		this.add(new UiMouseActivityComponent());
		this.add(new RemoveComponent());
	}
	
	public boolean mouseDown(int x, int y, int button) {
		this.getComponent(UiButtonDownComponent.class).down = true;
		return false;
	}
	
	public boolean mouseUp(int x, int y, int button) {
		if(this.getComponent(UiButtonDownComponent.class).down)
			this.mouseClick(button);
		this.getComponent(UiButtonDownComponent.class).down = false;
		return false;
	}
	
	public void mouseIn(int x, int y) {
	}
	
	public void mouseOut(int x, int y) {
	}
	
	public void mouseClick(int button) {
	}
}
