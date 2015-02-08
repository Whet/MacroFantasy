package com.mygdx.game.entities.ui;

import com.mygdx.game.actor.enums.CharacterValues.Stat;
import com.mygdx.game.components.primitive.TextComponent;

public class CharacterLabelEntity extends TextEntity {

	public CharacterLabelEntity() {

	}

	public void setLabel(Stat stat) {
		switch(stat) {
		case HEALTH : 
			this.getComponent(TextComponent.class).text = "HP";
			break;
		case GOLD:
			this.getComponent(TextComponent.class).text = "GP";
			break;
		case HAPPINESS:
			this.getComponent(TextComponent.class).text = "JY";
			break;
		case HUNGER:
			this.getComponent(TextComponent.class).text = "FD";
			break;
		case MANA:
			this.getComponent(TextComponent.class).text = "MP";
			break;
		default:
			this.getComponent(TextComponent.class).text = "??";
			break;
		}
	}

}
