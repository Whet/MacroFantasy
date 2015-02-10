package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Gender;
import com.mygdx.game.actor.enums.Need;

public class TraitLoneMaiden extends AbstractTrait {

	public TraitLoneMaiden() {
		super();
		name = "Lone Maiden";
		description = "You don't like other females..";
	}

	public void act(PartyCharacter pc) {
		boolean onlyFemale = true;
		for (PartyCharacter character : CharacterBank.characters) {
			if (character.getGender() == Gender.FEMALE && !character.equals(pc)){
				onlyFemale = false;
			}
		}
		if (!onlyFemale)
			pc.incrementNeed(Need.HAPPINESS, -5);
	}
}