package com.mygdx.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Job;
import com.mygdx.game.actor.enums.Need;
import com.mygdx.game.actor.enums.Stat;
import com.mygdx.game.actor.traits.AbstractTrait;
import com.mygdx.game.cards.AdventureBuilder;
import com.mygdx.game.cards.AdventureCard;
import com.mygdx.game.cards.CardMechanics;
import com.mygdx.game.cards.Choice;
import com.mygdx.game.cards.Location;
import com.mygdx.game.components.primitive.MultiRegionComponent;
import com.mygdx.game.components.primitive.MultiTextureComponent;
import com.mygdx.game.components.primitive.TextComponent;
import com.mygdx.game.components.primitive.TextureComponent;
import com.mygdx.game.components.ui.BarComponent;
import com.mygdx.game.components.ui.CardDisplayComponent;
import com.mygdx.game.components.ui.CharacterComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;
import com.mygdx.game.entities.ui.BarEntity;
import com.mygdx.game.entities.ui.CardEntity;
import com.mygdx.game.entities.ui.CharacterImageEntity;
import com.mygdx.game.entities.ui.CharacterLabelEntity;
import com.mygdx.game.entities.ui.CharacterNeedBarEntity;
import com.mygdx.game.entities.ui.CharacterTextEntity;
import com.mygdx.game.entities.ui.TextButtonEntity;
import com.mygdx.game.entities.ui.TextEntity;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.entities.ui.UiImageEntity;
import com.mygdx.game.utils.WordUtils;
import com.mygdx.game.utils.calendar.Calendar;

public class GameMenu extends Screen {

	public static final int CARD_CHOICES = 3;

	private CharacterBank characterBank;
	private CardMechanics cardMechanics;

	private UiImageEntity cardFrontEntity;

	private List<TextButtonEntity> cardButtons;
	private TextEntity cardName;
	private TextEntity cardDescription;

	private TextEntity date;

	private List<CharacterUi> characterUis;

	private CharacterStatMenu characterStatMenu;

	private Sound roosterSound;
	
	private Location location;

	private UiImageEntity backgroundEntity;


	public GameMenu(Location location, Engine engine, OrthographicCamera camera) {
		super(engine, camera);
		this.cardMechanics = new CardMechanics(this);
		characterBank = CharacterBank.getInstance();
		cardButtons = new ArrayList<TextButtonEntity>();
		characterUis = new ArrayList<CharacterUi>();
		this.location = location;
	}

	@Override
	protected void loadSystem() {

		Texture background = new Texture("mainMenuBackground.png");
		TextureRegion backgroundRegion = new TextureRegion(background);
		backgroundEntity = new UiImageEntity(0, 0, backgroundRegion);
		
		setBackground();
		
		engine.addEntity(backgroundEntity);

		roosterSound = Gdx.audio.newSound(Gdx.files.internal("sounds/rooster.ogg"));

		createDateBar();
		createButtons();
		createCharacterInfo();
		createCards();

	}

	private void setBackground() {
		
		Texture background = null;
		
		switch(this.location) {
			case FOREST:
				background = new Texture("forestBackground.png");
			break;
			case TUTORIAL:
				background = new Texture("mainMenuBackground.png");
			break;
			default:
			break;
		
		}
		
		TextureRegion backgroundRegion = new TextureRegion(background);
		backgroundEntity.getComponent(TextureComponent.class).region = backgroundRegion;
	}

	private void createDateBar() {
		date = new TextEntity();
		date.getComponent(TextComponent.class).visible = true;
		date.getComponent(TextComponent.class).text = Calendar.getInstance().getFullDate();
		date.getComponent(UiPositionComponent.class).x = 10;
		date.getComponent(UiPositionComponent.class).y = Gdx.graphics.getHeight() - 10;
		engine.addEntity(date);
	}

	public void updateDate() {
		date.getComponent(TextComponent.class).text = Calendar.getInstance().getFullDate();
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
				public boolean mouseDown(int x, int y) {
					cardMechanics.chooseCard(this.getComponent(CardDisplayComponent.class).card);
					showChosenCard();
					return super.mouseDown(x, y);
				}
			};
			card.getComponent(MultiTextureComponent.class).visible = false;
			engine.addEntity(card);
		}

		Texture cardFront = new Texture("cardFront.png");
		TextureRegion cardFrontTexture = new TextureRegion(cardFront);
		cardFrontEntity = new UiImageEntity(cardX + cardFrontTexture.getRegionWidth()/2, cardY - cardFrontTexture.getRegionHeight()/4, cardFrontTexture);
		cardFrontEntity.getComponent(TextureComponent.class).visible = false;
		engine.addEntity(cardFrontEntity);

		cardName = new TextEntity();
		cardName.getComponent(UiPositionComponent.class).x = cardFrontEntity.getComponent(UiPositionComponent.class).x + 20;
		cardName.getComponent(UiPositionComponent.class).y = cardFrontEntity.getComponent(UiPositionComponent.class).y + cardFrontTexture.getRegionHeight() - 20;
		cardName.getComponent(TextComponent.class).visible = false;
		engine.addEntity(cardName);

		cardDescription = new TextEntity();
		cardDescription.getComponent(TextComponent.class).maxCharsPerLine = 34;
		cardDescription.getComponent(UiPositionComponent.class).x = cardFrontEntity.getComponent(UiPositionComponent.class).x + 20;
		cardDescription.getComponent(UiPositionComponent.class).y = cardFrontEntity.getComponent(UiPositionComponent.class).y + cardFrontTexture.getRegionHeight() - 50;
		cardDescription.getComponent(TextComponent.class).visible = false;
		engine.addEntity(cardDescription);

		generateNewCards();
	}

	private void createCharacterInfo() {
		Texture head = new Texture("headPlaceholder.png");
		Texture body = new Texture("bodyPlaceholder.png");
		TextureRegion headTexture = new TextureRegion(head);
		TextureRegion bodyTexture = new TextureRegion(body);
		List<TextureRegion> bodyRegions = new ArrayList<TextureRegion>();
		bodyRegions.add(bodyTexture);
		bodyRegions.add(headTexture);

		addCharacterImage(50, Gdx.graphics.getHeight() - 200, bodyRegions, characterBank.getCharacters().get(0));
		addCharacterImage(50, Gdx.graphics.getHeight() - 600, bodyRegions, characterBank.getCharacters().get(1));
		addCharacterImage(Gdx.graphics.getWidth() - bodyTexture.getRegionWidth() - 50, Gdx.graphics.getHeight() - 200, bodyRegions, characterBank.getCharacters().get(2));
		addCharacterImage(Gdx.graphics.getWidth() - bodyTexture.getRegionWidth() - 50, Gdx.graphics.getHeight() - 600, bodyRegions, characterBank.getCharacters().get(3));
		addCharacterImage(Gdx.graphics.getWidth()/2 - bodyTexture.getRegionWidth()/2, 100, bodyRegions, characterBank.getCharacters().get(4));

		characterStatMenu = new CharacterStatMenu();
		Texture skillBackground = new Texture("statMenuBack.png");
		TextureRegion skillBackgroundTexture = new TextureRegion(skillBackground);

		int cardX = Gdx.graphics.getWidth()/2;
		int cardY = Gdx.graphics.getHeight()/2;

		int x = cardX - skillBackgroundTexture.getRegionWidth()/2;
		int y = cardY - skillBackgroundTexture.getRegionHeight()/4;

		characterStatMenu.background = new UiImageEntity(x, y, skillBackgroundTexture);
		characterStatMenu.background.getComponent(TextureComponent.class).visible = false;

		characterStatMenu.name = new TextEntity();

		characterStatMenu.name.getComponent(TextComponent.class).text = "NAME";
		characterStatMenu.name.getComponent(TextComponent.class).visible = false;
		characterStatMenu.name.getComponent(UiPositionComponent.class).x = x;
		characterStatMenu.name.getComponent(UiPositionComponent.class).y = y + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionHeight() - 20;

		characterStatMenu.needText = new TextEntity();
		characterStatMenu.needText.getComponent(TextComponent.class).text = "NEED";
		characterStatMenu.needText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.needText.getComponent(TextComponent.class).maxCharsPerLine = 999;
		characterStatMenu.needText.getComponent(UiPositionComponent.class).x = x;
		characterStatMenu.needText.getComponent(UiPositionComponent.class).y = y + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionHeight() - 40;

		characterStatMenu.statText = new TextEntity();
		characterStatMenu.statText.getComponent(TextComponent.class).text = "STAT";
		characterStatMenu.statText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.statText.getComponent(TextComponent.class).maxCharsPerLine = 999;
		characterStatMenu.statText.getComponent(UiPositionComponent.class).x = x;
		characterStatMenu.statText.getComponent(UiPositionComponent.class).y = y + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionHeight() - 60;

		characterStatMenu.skillText = new TextEntity();
		characterStatMenu.skillText.getComponent(TextComponent.class).text = "SKILL";
		characterStatMenu.skillText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.skillText.getComponent(TextComponent.class).maxCharsPerLine = 70;
		characterStatMenu.skillText.getComponent(UiPositionComponent.class).x = x;
		characterStatMenu.skillText.getComponent(UiPositionComponent.class).y = y + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionHeight() - 80;

		characterStatMenu.closeText = new TextButtonEntity() {
			@Override
			public boolean mouseDown(int x, int y) {
				hideStatMenu();
				return true;
			}
		};
		characterStatMenu.closeText.getComponent(TextComponent.class).text = "Close";
		characterStatMenu.closeText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.closeText.getComponent(UiPositionComponent.class).x = x + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionWidth() - 60;
		characterStatMenu.closeText.getComponent(UiPositionComponent.class).y = y + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionHeight() - 20;

		characterStatMenu.traitText = new TextEntity();
		characterStatMenu.traitText.getComponent(TextComponent.class).text = "Traits: @";
		characterStatMenu.traitText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.traitText.getComponent(TextComponent.class).maxCharsPerLine = 70;
		characterStatMenu.traitText.getComponent(UiPositionComponent.class).x = x;
		characterStatMenu.traitText.getComponent(UiPositionComponent.class).y = y + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionHeight() - 105;

		int iconX = x + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionWidth() - 90;
		int iconY = y + characterStatMenu.background.getComponent(TextureComponent.class).region.getRegionHeight() - 98;

		List<TextureRegion> region = new ArrayList<TextureRegion>();
		Texture icon1 = new Texture("healerIcon.png");
		TextureRegion icon1Texture = new TextureRegion(icon1);
		region.add(icon1Texture);
		region.add(icon1Texture);
		characterStatMenu.healerIcon = new UiButtonEntity(x, y, region) {
			@Override
			public boolean mouseDown(int x, int y) {
				if(!characterBank.isAssignedJob(Job.HEALER)) {
					characterStatMenu.getCharacter().assignJob(Job.HEALER);
					characterStatMenu.updateIcons(characterBank);
					updateCharacters();
				}
				return true;
			}
		};
		characterStatMenu.healerIcon.getComponent(UiPositionComponent.class).x = iconX;
		characterStatMenu.healerIcon.getComponent(UiPositionComponent.class).y = iconY;

		region = new ArrayList<TextureRegion>();
		icon1 = new Texture("alchemistIcon.png");
		icon1Texture = new TextureRegion(icon1);
		region.add(icon1Texture);
		region.add(icon1Texture);
		characterStatMenu.alchIcon = new UiButtonEntity(x, y, region) {
			@Override
			public boolean mouseDown(int x, int y) {
				if(!characterBank.isAssignedJob(Job.ALCHEMIST)) {
					characterStatMenu.getCharacter().assignJob(Job.ALCHEMIST);
					characterStatMenu.updateIcons(characterBank);
					updateCharacters();
				}
				return true;
			}
		};
		characterStatMenu.alchIcon.getComponent(UiPositionComponent.class).x = iconX;
		characterStatMenu.alchIcon.getComponent(UiPositionComponent.class).y = iconY - 40;

		region = new ArrayList<TextureRegion>();
		icon1 = new Texture("chefIcon.png");
		icon1Texture = new TextureRegion(icon1);
		region.add(icon1Texture);
		region.add(icon1Texture);
		characterStatMenu.cookIcon = new UiButtonEntity(x, y, region) {
			@Override
			public boolean mouseDown(int x, int y) {
				if(!characterBank.isAssignedJob(Job.COOK)) {
					characterStatMenu.getCharacter().assignJob(Job.COOK);
					characterStatMenu.updateIcons(characterBank);
					updateCharacters();
				}
				return true;
			}
		};
		characterStatMenu.cookIcon.getComponent(UiPositionComponent.class).x = iconX;
		characterStatMenu.cookIcon.getComponent(UiPositionComponent.class).y = iconY - 80;

		region = new ArrayList<TextureRegion>();
		icon1 = new Texture("bardIcon.png");
		icon1Texture = new TextureRegion(icon1);
		region.add(icon1Texture);
		region.add(icon1Texture);
		characterStatMenu.bardIcon = new UiButtonEntity(x, y, region) {
			@Override
			public boolean mouseDown(int x, int y) {
				if(!characterBank.isAssignedJob(Job.BARD)) {
					characterStatMenu.getCharacter().assignJob(Job.BARD);
					characterStatMenu.updateIcons(characterBank);
					updateCharacters();
				}
				return true;
			}
		};
		characterStatMenu.bardIcon.getComponent(UiPositionComponent.class).x = iconX;
		characterStatMenu.bardIcon.getComponent(UiPositionComponent.class).y = iconY - 120;

		region = new ArrayList<TextureRegion>();
		icon1 = new Texture("merchantIcon.png");
		icon1Texture = new TextureRegion(icon1);
		region.add(icon1Texture);
		region.add(icon1Texture);
		characterStatMenu.merchIcon = new UiButtonEntity(x, y, region) {
			@Override
			public boolean mouseDown(int x, int y) {
				if(!characterBank.isAssignedJob(Job.MERCHANT)) {
					characterStatMenu.getCharacter().assignJob(Job.MERCHANT);
					characterStatMenu.updateIcons(characterBank);
					updateCharacters();
				}
				return true;
			}
		};
		characterStatMenu.merchIcon.getComponent(UiPositionComponent.class).x = iconX;
		characterStatMenu.merchIcon.getComponent(UiPositionComponent.class).y = iconY - 160;

		characterStatMenu.alchIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.healerIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.cookIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.merchIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.bardIcon.getComponent(MultiTextureComponent.class).visible = false;

		engine.addEntity(characterStatMenu.background);
		engine.addEntity(characterStatMenu.closeText);
		engine.addEntity(characterStatMenu.name);
		engine.addEntity(characterStatMenu.needText);
		engine.addEntity(characterStatMenu.skillText);
		engine.addEntity(characterStatMenu.statText);
		engine.addEntity(characterStatMenu.traitText);

		engine.addEntity(characterStatMenu.healerIcon);
		engine.addEntity(characterStatMenu.alchIcon);
		engine.addEntity(characterStatMenu.cookIcon);
		engine.addEntity(characterStatMenu.bardIcon);
		engine.addEntity(characterStatMenu.merchIcon);
	}

	private void addCharacterImage(int x, int y, List<TextureRegion> bodyRegions, PartyCharacter character) {

		CharacterUi characterUI = new CharacterUi();

		//Shove bars up if character doesn't use magic
		int manaBarDisplacement = 15;
		if (character.hasMagic())
		{
			manaBarDisplacement = 0;
		}	

		CharacterImageEntity characterImg = new CharacterImageEntity(x, y, bodyRegions, cardMechanics);
		characterImg.setCharacter(character);
		engine.addEntity(characterImg);
		characterUI.image = characterImg;

		CharacterNeedBarEntity healthBar = new CharacterNeedBarEntity(x + 10, y - 15, 100, 10, 0, character.getTrueNeed(Need.HEALTH)) {

			@Override
			public int getValue() {

				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.MAXHEALTH);
				return this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.HEALTH);
			}
		};
		engine.addEntity(healthBar);
		healthBar.setCharacter(character);
		healthBar.getComponent(BarComponent.class).colour = Color.RED;
		characterUI.healthBar = healthBar;

		CharacterLabelEntity healthLabel = new CharacterLabelEntity();
		healthLabel.getComponent(UiPositionComponent.class).x = healthBar.getComponent(UiPositionComponent.class).x - 30;
		healthLabel.getComponent(UiPositionComponent.class).y = healthBar.getComponent(UiPositionComponent.class).y + 12;
		characterUI.healthLabel = healthLabel;
		healthLabel.setLabel(Need.HEALTH);
		engine.addEntity(healthLabel);

		if(character.hasMagic())
		{
			CharacterNeedBarEntity manaBar = new CharacterNeedBarEntity(x + 10, y - 30, 100, 10, 0, character.getTrueNeed(Need.MAXMANA)) {

				@Override
				public int getValue() {

					// Update min and max
					this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.MAXMANA);
					return this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.MANA);
				}
			};
			engine.addEntity(manaBar);
			manaBar.setCharacter(character);
			manaBar.getComponent(BarComponent.class).colour = Color.CYAN;
			characterUI.manaBar = manaBar;

			CharacterLabelEntity manaLabel = new CharacterLabelEntity();
			manaLabel.getComponent(UiPositionComponent.class).x = manaBar.getComponent(UiPositionComponent.class).x - 30;
			manaLabel.getComponent(UiPositionComponent.class).y = manaBar.getComponent(UiPositionComponent.class).y + 12;
			characterUI.manaLabel = manaLabel;
			manaLabel.setLabel(Need.MANA);
			engine.addEntity(manaLabel);
		}

		CharacterNeedBarEntity foodBar = new CharacterNeedBarEntity(x + 10, y - 45 + manaBarDisplacement, 100, 10, 0, character.getTrueNeed(Need.MAXHUNGER)) {


			@Override
			public int getValue() {

				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.MAXHUNGER);
				return this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.HUNGER);
			}
		};
		engine.addEntity(foodBar);
		foodBar.setCharacter(character);
		foodBar.getComponent(BarComponent.class).colour = Color.GREEN;
		characterUI.foodBar = foodBar;

		CharacterLabelEntity foodLabel = new CharacterLabelEntity();
		foodLabel.getComponent(UiPositionComponent.class).x = foodBar.getComponent(UiPositionComponent.class).x - 30;
		foodLabel.getComponent(UiPositionComponent.class).y = foodBar.getComponent(UiPositionComponent.class).y + 12;
		characterUI.foodLabel = foodLabel;
		foodLabel.setLabel(Need.HUNGER);
		engine.addEntity(foodLabel);

		CharacterNeedBarEntity happyBar = new CharacterNeedBarEntity(x + 10, y - 60 + manaBarDisplacement, 100, 10, 0, character.getTrueNeed(Need.MAXHAPPINESS)) {

			@Override
			public int getValue() {

				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.MAXHAPPINESS);
				return this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.HAPPINESS);
			}
		};
		engine.addEntity(happyBar);
		happyBar.setCharacter(character);
		happyBar.getComponent(BarComponent.class).colour = Color.YELLOW;
		characterUI.happyBar = happyBar;

		CharacterLabelEntity happyLabel = new CharacterLabelEntity();
		happyLabel.getComponent(UiPositionComponent.class).x = happyBar.getComponent(UiPositionComponent.class).x - 30;
		happyLabel.getComponent(UiPositionComponent.class).y = happyBar.getComponent(UiPositionComponent.class).y + 12;
		characterUI.happyLabel = happyLabel;
		happyLabel.setLabel(Need.HAPPINESS);
		engine.addEntity(happyLabel);

		CharacterNeedBarEntity goldBar = new CharacterNeedBarEntity(x + 10, y - 75 + manaBarDisplacement, 100, 10, 0, character.getTrueNeed(Need.MAXGOLD)) {

			@Override
			public int getValue() {

				// Update min and max
				this.getComponent(BarComponent.class).max = this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.MAXGOLD);
				return this.getComponent(CharacterComponent.class).character.getTrueNeed(Need.GOLD);
			}
		};
		engine.addEntity(goldBar);
		goldBar.setCharacter(character);
		goldBar.getComponent(BarComponent.class).colour = Color.DARK_GRAY;
		characterUI.goldBar = goldBar;

		CharacterLabelEntity goldLabel = new CharacterLabelEntity();
		goldLabel.getComponent(UiPositionComponent.class).x = goldBar.getComponent(UiPositionComponent.class).x - 30;
		goldLabel.getComponent(UiPositionComponent.class).y = goldBar.getComponent(UiPositionComponent.class).y + 12;
		characterUI.goldLabel = goldLabel;
		goldLabel.setLabel(Need.GOLD);
		engine.addEntity(goldLabel);

		CharacterTextEntity name = new CharacterTextEntity();
		name.getComponent(UiPositionComponent.class).x = x;
		name.getComponent(UiPositionComponent.class).y = y + characterImg.getComponent(MultiRegionComponent.class).regions.get(0).getRegionHeight() + 20;
		characterUI.name = name;
		name.setCharacter(character);
		engine.addEntity(name);

		this.characterUis.add(characterUI);
	}

	private void removeCharacter(CharacterUi characterUi, PartyCharacter character) {
		engine.removeEntity(characterUi.healthBar);
		engine.removeEntity(characterUi.healthLabel);
		if (character.hasMagic()) {
			engine.removeEntity(characterUi.manaBar);
			engine.removeEntity(characterUi.manaLabel);
		}
		engine.removeEntity(characterUi.happyBar);
		engine.removeEntity(characterUi.happyLabel);
		engine.removeEntity(characterUi.foodBar);
		engine.removeEntity(characterUi.foodLabel);
		engine.removeEntity(characterUi.goldBar);
		engine.removeEntity(characterUi.goldLabel);
		engine.removeEntity(characterUi.image);
		engine.removeEntity(characterUi.name);
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
			public boolean mouseDown(int x, int y) {
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
			public boolean mouseDown(int x, int y) {
				
				TravelScreen menu = new TravelScreen(engine, camera);
				menu.load();
				
				return true;
			}

		};
		engine.addEntity(travelButton);

		UiButtonEntity waitButton = new UiButtonEntity(btnX - (int)(btnUpTextureRegion.getRegionWidth() * 1.5), btnY, btnRegions) {

			{
				this.getComponent(TextComponent.class).text = "Wait";
			}

			@Override
			public boolean mouseDown(int x, int y) {
				cardMechanics.endTurn();
				roosterSound.stop();
				roosterSound.play();
				Calendar.getInstance().incrementDay(1);
				updateDate();
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
			buttons.get(i).getComponent(TextComponent.class).visible = false;
		}
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i).getComponent(MultiTextureComponent.class).visible = true;
		}
	}

	public void showChosenCard() {
		cardFrontEntity.getComponent(TextureComponent.class).visible = true;

		ImmutableArray<Entity> cards = engine.getEntitiesFor(Family.getFor(CardDisplayComponent.class));
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i).getComponent(MultiTextureComponent.class).visible = false;
		}

		AdventureCard chosenCard = cardMechanics.getChosenCard();

		cardName.getComponent(TextComponent.class).text = chosenCard.getTitle();
		cardDescription.getComponent(TextComponent.class).text = chosenCard.getDescription();

		cardName.getComponent(TextComponent.class).visible = true;
		cardDescription.getComponent(TextComponent.class).visible = true;

		// Remove old buttons
		for(TextButtonEntity textBtn:cardButtons) {
			engine.removeEntity(textBtn);
		}

		cardButtons = new ArrayList<TextButtonEntity>();

		for(Choice choice:chosenCard.getChoices()) {

			final Choice choiceFinal = choice;

			cardButtons.add(new TextButtonEntity() {

				{
					this.getComponent(TextComponent.class).text = choiceFinal.getText();
				}

				@Override
				public boolean mouseDown(int x, int y) {
					cardMechanics.makeChoice(choiceFinal);
					return true;
				}
			});
		}

		for(int i = 0; i < cardButtons.size(); i++) {
			engine.addEntity(cardButtons.get(i));
			cardButtons.get(i).getComponent(UiPositionComponent.class).x = cardName.getComponent(UiPositionComponent.class).x;
			cardButtons.get(i).getComponent(UiPositionComponent.class).y = cardFrontEntity.getComponent(UiPositionComponent.class).y + 40 + 20 * (i + 1);
		}
	}

	public void hideCards() {

		cardFrontEntity.getComponent(TextureComponent.class).visible = false;
		ImmutableArray<Entity> buttons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).getComponent(MultiTextureComponent.class).visible = true;
			buttons.get(i).getComponent(TextComponent.class).visible = true;
		}
		ImmutableArray<Entity> cards = engine.getEntitiesFor(Family.getFor(CardDisplayComponent.class));
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i).getComponent(MultiTextureComponent.class).visible = false;
		}
		characterStatMenu.alchIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.healerIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.cookIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.merchIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.bardIcon.getComponent(MultiTextureComponent.class).visible = false;

		cardName.getComponent(TextComponent.class).visible = false;
		cardDescription.getComponent(TextComponent.class).visible = false;

		// Remove old buttons
		for(TextButtonEntity textBtn:cardButtons) {
			engine.removeEntity(textBtn);
		}

		cardButtons = new ArrayList<TextButtonEntity>();
	}

	public void showStatMenu(PartyCharacter chosenCharacter) {

		characterStatMenu.character = chosenCharacter;

		ImmutableArray<Entity> buttons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).getComponent(MultiTextureComponent.class).visible = false;
			buttons.get(i).getComponent(TextComponent.class).visible = false;
		}

		characterStatMenu.background.getComponent(TextureComponent.class).visible = true;
		characterStatMenu.name.getComponent(TextComponent.class).visible = true;
		characterStatMenu.needText.getComponent(TextComponent.class).visible = true;
		characterStatMenu.statText.getComponent(TextComponent.class).visible = true;
		characterStatMenu.skillText.getComponent(TextComponent.class).visible = true;
		characterStatMenu.traitText.getComponent(TextComponent.class).visible = true;
		characterStatMenu.closeText.getComponent(TextComponent.class).visible = true;

		characterStatMenu.name.getComponent(TextComponent.class).text = chosenCharacter.getName() + ", " + WordUtils.capitalise(chosenCharacter.getGender().toString()) + " " + WordUtils.capitalise(chosenCharacter.getRace().toString());

		characterStatMenu.needText.getComponent(TextComponent.class).text = "Needs: "+ WordUtils.capitalise(Need.HEALTH.toString()) + " " + chosenCharacter.getTrueNeed(Need.HEALTH) + "/" + chosenCharacter.getTrueNeed(Need.MAXHEALTH) + " " +
				WordUtils.capitalise(Need.MANA.toString()) + " " + chosenCharacter.getTrueNeed(Need.MANA)  + "/" + chosenCharacter.getTrueNeed(Need.MAXMANA) + " " +
				WordUtils.capitalise(Need.HUNGER.toString()) + " " + chosenCharacter.getTrueNeed(Need.HUNGER)  + "/" + chosenCharacter.getTrueNeed(Need.MAXHUNGER) + " " +
				WordUtils.capitalise(Need.HAPPINESS.toString()) + " " + chosenCharacter.getTrueNeed(Need.HAPPINESS) + "/" + chosenCharacter.getTrueNeed(Need.MAXHAPPINESS) + " " +
				WordUtils.capitalise(Need.GOLD.toString()) + " " + chosenCharacter.getTrueNeed(Need.GOLD) + "/" + chosenCharacter.getTrueNeed(Need.MAXGOLD) ;

		characterStatMenu.statText.getComponent(TextComponent.class).text = "Stats: "+ WordUtils.capitalise(Stat.COMBAT.toString()) + " " + chosenCharacter.getTrueStat(Stat.COMBAT) + " " +
				WordUtils.capitalise(Stat.CHARISMA.toString()) + " " + chosenCharacter.getTrueStat(Stat.COMBAT) + " " +
				WordUtils.capitalise(Stat.SNEAK.toString()) + " " + chosenCharacter.getTrueStat(Stat.SNEAK) + " " +
				WordUtils.capitalise(Stat.PATHFINDER.toString()) + " " + chosenCharacter.getTrueStat(Stat.PATHFINDER) + " " +
				WordUtils.capitalise(Stat.LUCK.toString()) + " " + chosenCharacter.getTrueStat(Stat.LUCK);

		characterStatMenu.skillText.getComponent(TextComponent.class).text = "Skills: "+ WordUtils.capitalise(Job.ALCHEMIST.toString()) + " " + chosenCharacter.getSkill(Job.ALCHEMIST) + " " +
				WordUtils.capitalise(Job.BARD.toString()) + " " + chosenCharacter.getSkill(Job.BARD) + " " +
				WordUtils.capitalise(Job.COOK.toString()) + " " + chosenCharacter.getSkill(Job.COOK) + " " +
				WordUtils.capitalise(Job.HEALER.toString()) + " " + chosenCharacter.getSkill(Job.HEALER) + " " +
				WordUtils.capitalise(Job.MERCHANT.toString()) + " " + chosenCharacter.getSkill(Job.MERCHANT);
		for (int i = 0; i < chosenCharacter.getTraits().size(); i++)
		{
			if (i == 0)
				characterStatMenu.traitText.getComponent(TextComponent.class).text = chosenCharacter.getTraits().get(i).getName() + ": " + chosenCharacter.getTraits().get(i).getDescription() + "@";
			else
				characterStatMenu.traitText.getComponent(TextComponent.class).text += chosenCharacter.getTraits().get(i).getName() + ": " + chosenCharacter.getTraits().get(i).getDescription() + "@";			
		}
		characterStatMenu.alchIcon.getComponent(MultiTextureComponent.class).visible = true;
		characterStatMenu.healerIcon.getComponent(MultiTextureComponent.class).visible = true;
		characterStatMenu.cookIcon.getComponent(MultiTextureComponent.class).visible = true;
		characterStatMenu.merchIcon.getComponent(MultiTextureComponent.class).visible = true;
		characterStatMenu.bardIcon.getComponent(MultiTextureComponent.class).visible = true;

		characterStatMenu.updateIcons(characterBank);

	}

	public void hideStatMenu() {

		ImmutableArray<Entity> buttons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).getComponent(MultiTextureComponent.class).visible = true;
			buttons.get(i).getComponent(TextComponent.class).visible = true;
		}
		ImmutableArray<Entity> cards = engine.getEntitiesFor(Family.getFor(CardDisplayComponent.class));
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i).getComponent(MultiTextureComponent.class).visible = false;
		}

		characterStatMenu.background.getComponent(TextureComponent.class).visible = false;
		characterStatMenu.name.getComponent(TextComponent.class).visible = false;
		characterStatMenu.needText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.statText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.skillText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.traitText.getComponent(TextComponent.class).visible = false;
		characterStatMenu.closeText.getComponent(TextComponent.class).visible = false;

		characterStatMenu.alchIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.healerIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.cookIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.merchIcon.getComponent(MultiTextureComponent.class).visible = false;
		characterStatMenu.bardIcon.getComponent(MultiTextureComponent.class).visible = false;
	}

	public void generateNewCards() {
		ImmutableArray<Entity> cards = engine.getEntitiesFor(Family.getFor(CardDisplayComponent.class));
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i).getComponent(CardDisplayComponent.class).card = AdventureBuilder.getNewAdventure(location, cardMechanics, characterBank);
		}
	}

	public void updateCharacters() {
		boolean characterGone = false;
		ArrayList<CharacterUi> deadCharacterUis = new ArrayList<CharacterUi>();
		for(CharacterUi characterUi:this.characterUis) {
			PartyCharacter character = characterUi.image.getComponent(CharacterComponent.class).character;	
			characterUi.name.setCharacter(characterUi.name.getComponent(CharacterComponent.class).character);
			if(!character.isAlive()) {
				// Show dead icon
				characterGone = true;
				deadCharacterUis.add(characterUi);
				removeCharacter(characterUi, character);
			}
		}

		if (characterGone) {
			String message = "";
			for (CharacterUi characterUi : deadCharacterUis)
			{
				PartyCharacter character = characterUi.image.getComponent(CharacterComponent.class).character;	
				switch(character.getDeathType())
				{
				case DEATH:
					message += "@" + character.getName() + " died";
					switch (character.getCauseOfDeath())
					{
					case FOOD:
						message += " because of hunger. You monster!";
						break;
					case GOLD:
						message += " because " + character.getPronoun() + " ran out of gold.";
						break;
					case HAPPINESS:
						message += " because " + character.getPronoun() + " was unhappy.";
						break;
					case HEALTH:
						message += " due to their wounds.";
						break;
					default:
						message += " because... erm.. I don't know. How'd did you get here?";
						break;
					}
					characterUi.name.getComponent(TextComponent.class).text = character.getName() + " is dead D:";	
					break;
				case DESERTION:
					message += "@" + character.getName() + " ran away";
					switch (character.getCauseOfDeath())
					{
					case FOOD:
						message += " because of hunger. You monster!";
						break;
					case GOLD:
						message += " because " + character.getPronoun() + " ran out of gold.";
						break;
					case HAPPINESS:
						message += " because " + character.getPronoun() + " was unhappy.";
						break;
					case HEALTH:
						message += " due to their wounds.";
						break;
					default:
						message += " because... erm.. I don't know. How'd did you get here?";
						break;
					}
					characterUi.name.getComponent(TextComponent.class).text = character.getName() + " left the party :(";
					break;
				}
				characterBank.removeCharacter(character);
				characterBank.addCharacter(new PartyCharacter());
				this.characterUis.remove(characterUi);
			}
			new PopUpMessage(message);
		}
	}

	private static class CharacterUi {
		public TextEntity healthLabel, manaLabel, foodLabel, happyLabel, goldLabel;
		public CharacterTextEntity name;
		public CharacterImageEntity image;
		public BarEntity healthBar, manaBar, foodBar, happyBar, goldBar;
	}

	private static class CharacterStatMenu {

		public PartyCharacter character;

		public TextButtonEntity closeText;
		public TextEntity name, skillText, needText, statText, traitText;
		public UiImageEntity background;
		public UiButtonEntity healerIcon, alchIcon, cookIcon, bardIcon, merchIcon;

		public PartyCharacter getCharacter() {
			return this.character;
		}

		public void updateIcons(CharacterBank characterBank) {

			if(characterBank.isAssignedJob(Job.ALCHEMIST)) {
				alchIcon.getComponent(MultiTextureComponent.class).visible = false;
			}
			else {
				alchIcon.getComponent(MultiTextureComponent.class).visible = true;
			}

			if(characterBank.isAssignedJob(Job.BARD)) {
				bardIcon.getComponent(MultiTextureComponent.class).visible = false;
			}
			else {
				bardIcon.getComponent(MultiTextureComponent.class).visible = true;
			}

			if(characterBank.isAssignedJob(Job.COOK)) {
				cookIcon.getComponent(MultiTextureComponent.class).visible = false;
			}
			else {
				cookIcon.getComponent(MultiTextureComponent.class).visible = true;
			}

			if(characterBank.isAssignedJob(Job.HEALER)) {
				healerIcon.getComponent(MultiTextureComponent.class).visible = false;
			}
			else {
				healerIcon.getComponent(MultiTextureComponent.class).visible = true;
			}

			if(characterBank.isAssignedJob(Job.MERCHANT)) {
				merchIcon.getComponent(MultiTextureComponent.class).visible = false;
			}
			else {
				merchIcon.getComponent(MultiTextureComponent.class).visible = true;
			}

			if(character.getJob() != null)
				switch(character.getJob()) {
				case ALCHEMIST:
					alchIcon.getComponent(MultiTextureComponent.class).visible = true;
					break;
				case BARD:
					bardIcon.getComponent(MultiTextureComponent.class).visible = true;
					break;
				case COOK:
					cookIcon.getComponent(MultiTextureComponent.class).visible = true;
					break;
				case HEALER:
					healerIcon.getComponent(MultiTextureComponent.class).visible = true;
					break;
				case MERCHANT:
					merchIcon.getComponent(MultiTextureComponent.class).visible = true;
					break;
				default:
					break;
				}
		}
	}

	private class PopUpMessage  {

		private TextButtonEntity closeText;
		private TextEntity message;
		private UiImageEntity background;

		public PopUpMessage(String text) {

			ImmutableArray<Entity> buttons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
			for(int i = 0; i < buttons.size(); i++) {
				buttons.get(i).getComponent(MultiTextureComponent.class).visible = false;
				buttons.get(i).getComponent(TextComponent.class).visible = false;
			}

			Texture skillBackground = new Texture("popUpBack.png");
			TextureRegion skillBackgroundTexture = new TextureRegion(skillBackground);

			int x = Gdx.graphics.getWidth()/2 - skillBackgroundTexture.getRegionWidth()/2;
			int y = Gdx.graphics.getHeight()/2 - skillBackgroundTexture.getRegionHeight()/4;


			background = new UiImageEntity(x, y, skillBackgroundTexture);
			background.getComponent(TextureComponent.class).visible = true;

			message = new TextEntity();
			message.getComponent(TextComponent.class).text = text;
			message.getComponent(TextComponent.class).visible = true;
			message.getComponent(UiPositionComponent.class).x = x;
			message.getComponent(UiPositionComponent.class).y = y + background.getComponent(TextureComponent.class).region.getRegionHeight() - 20;


			closeText = new TextButtonEntity() {
				@Override
				public boolean mouseDown(int x, int y) {
					hidePopUp();
					return true;
				}
			};
			closeText.getComponent(TextComponent.class).text = "Close";
			closeText.getComponent(TextComponent.class).visible = true;
			closeText.getComponent(UiPositionComponent.class).x = x + background.getComponent(TextureComponent.class).region.getRegionWidth() - 60;
			closeText.getComponent(UiPositionComponent.class).y = y + background.getComponent(TextureComponent.class).region.getRegionHeight() - 20;

			engine.addEntity(background);
			engine.addEntity(closeText);
			engine.addEntity(message);
		}

		private void hidePopUp() {

			ImmutableArray<Entity> buttons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, MultiTextureComponent.class, UiMouseActivityComponent.class, TextComponent.class));
			for(int i = 0; i < buttons.size(); i++) {
				buttons.get(i).getComponent(MultiTextureComponent.class).visible = true;
				buttons.get(i).getComponent(TextComponent.class).visible = true;
			}
			ImmutableArray<Entity> cards = engine.getEntitiesFor(Family.getFor(CardDisplayComponent.class));
			for(int i = 0; i < cards.size(); i++) {
				cards.get(i).getComponent(MultiTextureComponent.class).visible = false;
			}

			characterStatMenu.alchIcon.getComponent(MultiTextureComponent.class).visible = false;
			characterStatMenu.healerIcon.getComponent(MultiTextureComponent.class).visible = false;
			characterStatMenu.cookIcon.getComponent(MultiTextureComponent.class).visible = false;
			characterStatMenu.merchIcon.getComponent(MultiTextureComponent.class).visible = false;
			characterStatMenu.bardIcon.getComponent(MultiTextureComponent.class).visible = false;

			engine.removeEntity(background);
			engine.removeEntity(closeText);
			engine.removeEntity(message);

		}
	}


}
