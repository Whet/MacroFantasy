package com.mygdx.game.components.primitive;

import java.util.List;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MultiRegionComponent extends Component {
	public List<TextureRegion> regions;
	public boolean visible;

	public MultiRegionComponent (List<TextureRegion> regions) {
		this.regions = regions;
		this.visible = true;
	}
}
