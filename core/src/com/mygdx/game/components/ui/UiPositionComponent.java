package com.mygdx.game.components.ui;

import com.badlogic.ashley.core.Component;

public class UiPositionComponent extends Component {
	
	public int x;
	public int y;
	public int z;

	public UiPositionComponent(int xPosition, int yPosition) {
		this.x = xPosition;
		this.y = yPosition;
		this.z = 0;
	}
	
	public UiPositionComponent (int xPosition, int yPosition, int drawZ) {
		this.x = xPosition;
		this.y = yPosition;
		this.z = drawZ;
	}

}
