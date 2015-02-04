package com.mygdx.game.components.ui;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

public class BarComponent extends Component {

	public int width, height, min, max;
	public boolean visible;
	public Color colour;
	
	public BarComponent(int width, int height, int min, int max) {
		this.width = width;
		this.height = height;
		this.min = min;
		this.max = max;
		this.visible = true;
		this.colour = Color.RED;
	}
	
}
