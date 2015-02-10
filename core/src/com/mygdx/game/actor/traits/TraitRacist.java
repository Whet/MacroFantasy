package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Need;

public class TraitRacist extends AbstractTrait {

	public TraitRacist() {
		super();
		name = "Racist";
		description = "You are a racist.";
	}

	public void act(PartyCharacter pc) {
		boolean otherRacePresent = false;
		for (PartyCharacter character : CharacterBank.characters) {
			if (character.getRace() != pc.getRace()){
				otherRacePresent = true;
			}
		}
		if (otherRacePresent)
			pc.incrementNeed(Need.HAPPINESS, -15);
	}
}
