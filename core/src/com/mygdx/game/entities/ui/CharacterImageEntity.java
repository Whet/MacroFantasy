package com.mygdx.game.entities.ui;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.cards.CardMechanics;
import com.mygdx.game.components.ui.CharacterComponent;

public class CharacterImageEntity extends UiMultiButtonEntity {

	private CardMechanics cardMechanics;

	public CharacterImageEntity(int x, int y, List<TextureRegion> region, CardMechanics cardMechanics) {
		super(x, y, region);
		this.cardMechanics = cardMechanics;
	}
	
	public void setCharacter(PartyCharacter character) {
		this.add(new CharacterComponent(character));
	}
	
	@Override
	public void mouseClick(int button) {
		cardMechanics.chooseCharacter(this.getComponent(CharacterComponent.class).character);
	}
}
