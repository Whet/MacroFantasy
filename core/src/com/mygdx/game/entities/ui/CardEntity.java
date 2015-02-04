package com.mygdx.game.entities.ui;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.cards.AdventureCard;
import com.mygdx.game.components.primitive.MultiTextureComponent;
import com.mygdx.game.components.ui.CardDisplayComponent;

public class CardEntity extends UiButtonEntity {
	
	public CardEntity(int x, int y, List<TextureRegion> region) {
		super(x, y, region);
		this.add(new CardDisplayComponent(null));
	}
	
	@Override
	public boolean mD(int x, int y) {
		return false;
	}
	
	@Override
	public boolean mU(int x, int y) {
		return false;
	}
	
	@Override
	public void mI(int x, int y) {
		this.getComponent(MultiTextureComponent.class).frame = 1;
	}
	
	@Override
	public void mO(int x, int y) {
		this.getComponent(MultiTextureComponent.class).frame = 0;
	}
	
	public void setCard(AdventureCard card) {
		this.add(new CardDisplayComponent(card));
	}

}
