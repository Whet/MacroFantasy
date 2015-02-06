package com.mygdx.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.Character;
import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.components.primitive.MultiTextureComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.ui.BarComponent;
import com.mygdx.game.components.ui.CardDisplayComponent;
import com.mygdx.game.components.ui.CharacterComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;
import com.mygdx.game.entities.ui.CardEntity;
import com.mygdx.game.entities.ui.CharacterImageEntity;
import com.mygdx.game.entities.ui.CharacterStatBarEntity;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.entities.ui.UiImageEntity;

public class GameMenu extends Screen {
	
	public static final int CARD_CHOICES = 3;
	
	private CharacterBank characterBank;

	public GameMenu(Engine engine, OrthographicCamera camera) {
		super(engine, camera);
		this.characterBank = new CharacterBank();
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
		Texture head = new Texture("headPlaceholder.png");
		Texture body = new Texture("bodyPlaceholder.png");
		TextureRegion headTexture = new TextureRegion(head);
		TextureRegion bodyTexture = new TextureRegion(body);
		List<TextureRegion> bodyRegions = new ArrayList<TextureRegion>();
		bodyRegions.add(bodyTexture);
		bodyRegions.add(headTexture);
		
		addCharacterImage(50, Gdx.graphics.getHeight() - 200, bodyRegions, characterBank.characters.get(0));
		addCharacterImage(50, Gdx.graphics.getHeight() - 600, bodyRegions, characterBank.characters.get(1));
		addCharacterImage(Gdx.graphics.getWidth() - bodyTexture.getRegionWidth() - 50, Gdx.graphics.getHeight() - 200, bodyRegions, characterBank.characters.get(2));
		addCharacterImage(Gdx.graphics.getWidth() - bodyTexture.getRegionWidth() - 50, Gdx.graphics.getHeight() - 600, bodyRegions, characterBank.characters.get(3));
		addCharacterImage(Gdx.graphics.getWidth()/2 - bodyTexture.getRegionWidth()/2, 100, bodyRegions, characterBank.characters.get(4));
		
	}

	private void addCharacterImage(int x, int y, List<TextureRegion> bodyRegions, Character character) {
		CharacterImageEntity characterImg = new CharacterImageEntity(x, y, bodyRegions);
		characterImg.setCharacter(character);
		engine.addEntity(characterImg);
		
		CharacterStatBarEntity healthBar = new CharacterStatBarEntity(x + 10, y - 15, 100, 10, 0, character.getMaxHealth()) {
			
			@Override
			public int getValue() {
				
				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getMaxHealth();
				return this.getComponent(CharacterComponent.class).character.getHealth();
			}
		};
		engine.addEntity(healthBar);
		healthBar.setCharacter(character);
		healthBar.getComponent(BarComponent.class).colour = Color.RED;
		
		CharacterStatBarEntity manaBar = new CharacterStatBarEntity(x + 10, y - 30, 100, 10, 0, character.getMaxMana()) {
			
			@Override
			public int getValue() {
				
				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getMaxMana();
				return this.getComponent(CharacterComponent.class).character.getMana();
			}
		};
		engine.addEntity(manaBar);
		manaBar.setCharacter(character);
		manaBar.getComponent(BarComponent.class).colour = Color.CYAN;
		
		CharacterStatBarEntity foodBar = new CharacterStatBarEntity(x + 10, y - 45, 100, 10, 0, character.getMaxHunger()) {
			
			@Override
			public int getValue() {
				
				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getMaxHunger();
				return this.getComponent(CharacterComponent.class).character.getHunger();
			}
		};
		engine.addEntity(foodBar);
		foodBar.setCharacter(character);
		foodBar.getComponent(BarComponent.class).colour = Color.GREEN;
		
		CharacterStatBarEntity happyBar = new CharacterStatBarEntity(x + 10, y - 60, 100, 10, 0, character.getMaxHappiness()) {
			
			@Override
			public int getValue() {
				
				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getMaxHappiness();
				return this.getComponent(CharacterComponent.class).character.getHappiness();
			}
		};
		engine.addEntity(happyBar);
		happyBar.setCharacter(character);
		happyBar.getComponent(BarComponent.class).colour = Color.YELLOW;
		
		CharacterStatBarEntity goldBar = new CharacterStatBarEntity(x + 10, y - 75, 100, 10, 0, character.getMaxGold()) {
			
			@Override
			public int getValue() {
				
				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getMaxGold();
				return this.getComponent(CharacterComponent.class).character.getGold();
			}
		};
		engine.addEntity(goldBar);
		goldBar.setCharacter(character);
		goldBar.getComponent(BarComponent.class).colour = Color.DARK_GRAY;
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
	}

}
