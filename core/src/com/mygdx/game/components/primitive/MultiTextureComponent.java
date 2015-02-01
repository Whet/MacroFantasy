package com.mygdx.game.components.primitive;

import java.util.List;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MultiTextureComponent extends Component {
	public List<TextureRegion> regions;
	public int frame;

	public MultiTextureComponent (List<TextureRegion> regions) {
		this.regions = regions;
		this.frame = 0;
	}
}
