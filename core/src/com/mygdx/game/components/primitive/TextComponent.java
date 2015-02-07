package com.mygdx.game.components.primitive;

import com.badlogic.ashley.core.Component;

public class TextComponent extends Component {

	public String text;
	public int maxCharsPerLine;
	public boolean visible;
	
	public TextComponent(String text) {
		this.text = text;
		this.visible = true;
		this.maxCharsPerLine = 40;
	}
	
}
