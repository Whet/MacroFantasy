package com.mygdx.game.components.primitive;

import com.badlogic.ashley.core.Component;

public class TextComponent extends Component {

	public String text;
	
	public TextComponent(String text) {
		this.text = text;
	}
	
}
