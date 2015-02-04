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
		
		createButtons();
		
		
	}

	private void createButtons() {
		Texture btnUp = new Texture("btnUp.png");
		Texture btnDown = new Texture("btnDown.png");
		TextureRegion btnUpTextureRegion = new TextureRegion(btnUp);
		TextureRegion btnDownTextureRegion = new TextureRegion(btnDown);
		List<TextureRegion> btnRegions = new ArrayList<TextureRegion>();
		btnRegions.add(btnUpTextureRegion);
		btnRegions.add(btnDownTextureRegion);
		
		int btnX = Gdx.graphics.getWidth()/2 - btnUpTextureRegion.getRegionWidth()/2;
		int btnY = Gdx.graphics.getHeight()/2;
		
		UiButtonEntity adventureButton = new UiButtonEntity(btnX, btnY, btnRegions) {
			
			{
				this.getComponent(TextComponent.class).text = "Adventure";
			}
			
			@Override
			public boolean mD(int x, int y) {
				return true;
			}
			
		};
		engine.addEntity(adventureButton);
		
		UiButtonEntity travelButton = new UiButtonEntity(btnX + (int)(btnUpTextureRegion.getRegionWidth() * 1.5), btnY, btnRegions) {
			
			{
				this.getComponent(TextComponent.class).text = "Travel";
			}
			
			@Override
			public boolean mD(int x, int y) {
				return true;
			}
			
		};
		engine.addEntity(travelButton);
		
		UiButtonEntity waitButton = new UiButtonEntity(btnX - (int)(btnUpTextureRegion.getRegionWidth() * 1.5), btnY, btnRegions) {
			
			{
				this.getComponent(TextComponent.class).text = "Wait";
			}
			
			@Override
			public boolean mD(int x, int y) {
				return true;
			}
			
		};
		engine.addEntity(waitButton);
	}

}
