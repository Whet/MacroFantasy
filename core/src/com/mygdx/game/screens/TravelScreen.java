package com.mygdx.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.cards.Location;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.entities.ui.UiImageEntity;

public class TravelScreen extends Screen {

	private UiImageEntity backgroundEntity;
	
	public TravelScreen(Engine engine, OrthographicCamera camera) {
		super(engine, camera);
	}

	@Override
	protected void loadSystem() {
		Texture background = new Texture("travelBackground.png");
		TextureRegion backgroundRegion = new TextureRegion(background);
		backgroundEntity = new UiImageEntity(0, 0, backgroundRegion);
		this.engine.addEntity(backgroundEntity);
		
		Texture travelNodeOn = new Texture("travelNodeOn.png");
		TextureRegion travelNodeOnRegion = new TextureRegion(travelNodeOn);
		
		Texture travelNodeOff = new Texture("travelNodeOff.png");
		TextureRegion travelNodeOffRegion = new TextureRegion(travelNodeOff);
		
		makeTravelButton(1000,80,Location.TUTORIAL, travelNodeOnRegion, travelNodeOffRegion);
		makeTravelButton(180,250,Location.FOREST, travelNodeOnRegion, travelNodeOffRegion);
	}

	private void makeTravelButton(int x, int y, final Location location, TextureRegion travelNodeOnRegion, TextureRegion travelNodeOffRegion) {
		List<TextureRegion> regions = new ArrayList<TextureRegion>();
		regions.add(travelNodeOffRegion);
		regions.add(travelNodeOnRegion);
		UiButtonEntity button = new UiButtonEntity(x, y, regions) {
			@Override
			public void mouseClick(int button) {
				GameMenu gameMenu = new GameMenu(location, engine, camera);
				gameMenu.load();
			}
		};
		engine.addEntity(button);
	}

}
