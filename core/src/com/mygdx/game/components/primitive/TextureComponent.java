package com.mygdx.game.components.primitive;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent extends Component {
	public TextureRegion region;
	public boolean visible;

	public TextureComponent (TextureRegion region) {
		this.region = region;
		this.visible = true;
	}
}
