package com.mygdx.game.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.ui.UiImageEntity;

public class GameMenu extends Screen {

	public GameMenu(Engine engine, OrthographicCamera camera) {
		super(engine, camera);
	}

	@Override
	protected void loadSystem() {
		
		Texture background = new Texture("mainMenuBackground.png");

		TextureRegion backgroundRegion = new TextureRegion(background);
		
		UiImageEntity backgroundEntity = new UiImageEntity(0, 0, backgroundRegion);

		engine.addEntity(backgroundEntity);
	}

}
