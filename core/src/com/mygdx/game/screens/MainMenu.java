package com.mygdx.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.entities.ui.UiImageEntity;
import com.mygdx.game.systems.ui.UiSystem;

public class MainMenu extends Screen {

	public MainMenu(Engine engine, OrthographicCamera camera) {
		super(engine, camera);
	}
	
	@Override
	public void loadSystem() {
		
		// Ui system handles drawing the ui objects and mouse events
		engine.addSystem(new UiSystem(camera));

		Texture btnUp = new Texture("btnUp.png");
		Texture btnDown = new Texture("btnDown.png");
		
		Texture background = new Texture("mainMenuBackground.png");

		TextureRegion btnUpTextureRegion = new TextureRegion(btnUp);
		TextureRegion btnDownTextureRegion = new TextureRegion(btnDown);
		
		TextureRegion backgroundRegion = new TextureRegion(background);
		
		List<TextureRegion> btnRegions = new ArrayList<TextureRegion>();
		btnRegions.add(btnUpTextureRegion);
		btnRegions.add(btnDownTextureRegion);
		
		UiButtonEntity exitButton = new UiButtonEntity(200, Gdx.graphics.getHeight() - 500, btnRegions) {
			
			{
				this.getComponent(TextComponent.class).text = "Exit";
			}
			
			@Override
			public boolean mouseDown(int x, int y) {
				System.exit(0);
				return true;
			}
			
		};

		engine.addEntity(exitButton);
		
		UiButtonEntity startButton = new UiButtonEntity(200, Gdx.graphics.getHeight() - 400, btnRegions) {
			
			{
				this.getComponent(TextComponent.class).text = "Start";
			}
			
			@Override
			public boolean mouseDown(int x, int y) {
				GameMenu menu = new GameMenu(engine, camera);
				menu.load();
				return true;
			}
			
		};

		engine.addEntity(startButton);
		
		UiImageEntity backgroundEntity = new UiImageEntity(0, 0, backgroundRegion);

		engine.addEntity(backgroundEntity);
		
	}

}
