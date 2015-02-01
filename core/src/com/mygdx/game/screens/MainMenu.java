package com.mygdx.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.systems.ui.UiSystem;

public class MainMenu extends Screen {

	private Engine engine;
	private OrthographicCamera camera;

	public MainMenu(Engine engine, OrthographicCamera camera) {
		this.engine = engine;
		this.camera = camera;
	}
	
	@Override
	public void load() {
		
		engine.addSystem(new UiSystem(camera));

		Texture btnUp = new Texture("btnUp.png");
		Texture btnDown = new Texture("btnDown.png");

		TextureRegion btnUpTextureRegion = new TextureRegion(btnUp);
		TextureRegion btnDownTextureRegion = new TextureRegion(btnDown);
		
		List<TextureRegion> btnRegions = new ArrayList<TextureRegion>();
		btnRegions.add(btnUpTextureRegion);
		btnRegions.add(btnDownTextureRegion);
		
		UiButtonEntity exitButton = new UiButtonEntity(200, Gdx.graphics.getHeight() - 500, btnRegions) {
			
			@Override
			public boolean mD(int x, int y) {
				System.exit(0);
				return true;
			}
			
		};

		engine.addEntity(exitButton);
		
	}

}
