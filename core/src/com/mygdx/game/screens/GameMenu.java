package com.mygdx.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.components.primitive.MultiTextureComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.ui.CardDisplayComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;
import com.mygdx.game.entities.ui.CardEntity;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.entities.ui.UiImageEntity;

public class GameMenu extends Screen {
	
	public static final int CARD_CHOICES = 3;

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
		createCharacterInfo();
		createCards();
		
	}

	private void createCards() {
		Texture cardOff = new Texture("cardBack.png");
		Texture cardIn = new Texture("cardBackSelected.png");
		TextureRegion cardOffTexture = new TextureRegion(cardOff);
		TextureRegion cardInTexture = new TextureRegion(cardIn);
		List<TextureRegion> cardRegions = new ArrayList<TextureRegion>();
		cardRegions.add(cardOffTexture);
		cardRegions.add(cardInTexture);
		
		int cardX = Gdx.graphics.getWidth()/2 - cardOffTexture.getRegionWidth() * 2;
		int cardY = Gdx.graphics.getHeight()/2;
		
		for(int i = 0; i < CARD_CHOICES; i++) {
			CardEntity card = new CardEntity(cardX + (int)(cardOffTexture.getRegionWidth() * 1.5 * i), cardY, cardRegions) {
				@Override
				public boolean mD(int x, int y) {
					hideCards();
					return super.mD(x, y);
				}
			};
			card.getComponent(MultiTextureComponent.class).visible = false;
			engine.addEntity(card);
		}
	}

	private void createCharacterInfo() {
		// TODO Auto-generated method stub
		
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
				showCards();
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
	
	public void showCards() {
		ImmutableArray<Entity> buttons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
		ImmutableArray<Entity> cards = engine.getEntitiesFor(Family.getFor(CardDisplayComponent.class));
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).getComponent(MultiTextureComponent.class).visible = false;
		}
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i).getComponent(MultiTextureComponent.class).visible = true;
		}
		System.out.println("Show");
	}
	
	public void hideCards() {
		ImmutableArray<Entity> buttons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
		ImmutableArray<Entity> cards = engine.getEntitiesFor(Family.getFor(CardDisplayComponent.class));
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).getComponent(MultiTextureComponent.class).visible = true;
		}
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i).getComponent(MultiTextureComponent.class).visible = false;
		}
		System.out.println("Hide");
	}

}
