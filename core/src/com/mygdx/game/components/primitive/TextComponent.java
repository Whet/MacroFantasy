package com.mygdx.game.components.primitive;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class TextComponent extends Component {

	public String text;
	public int maxCharsPerLine;
	public boolean visible;
	public BitmapFont font;
	
	public TextComponent(String text, BitmapFont font) {
		this.text = text;
		this.visible = true;
		this.maxCharsPerLine = 40;
		this.font = font;
	}
	
	public TextComponent(String text) {
		this.text = text;
		this.visible = true;
		this.maxCharsPerLine = 40;
		this.font = new BitmapFont(Gdx.files.internal("althea.fnt"), Gdx.files.internal("althea.png"), false);
		this.font.setScale(0.7f);
	}
}
