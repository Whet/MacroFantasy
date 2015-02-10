package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Need;

public class TraitConjureFeast extends AbstractTrait {
	public TraitConjureFeast() {
		super();
		name = "Conjure Feast";
		description = "In times of need you can conjure a feast to feed the party.";
		flags.add(TraitFlag.POSITIVE);
	}
	
	public void act(PartyCharacter pc) {
		boolean isSomeoneStarving = false;
		for (PartyCharacter character : CharacterBank.characters) {
			if (character.getTrueNeed(Need.HUNGER) < 10)
				isSomeoneStarving = true;
		}
		if (isSomeoneStarving && pc.getBaseNeed(Need.MANA) > 20) {
			pc.incrementNeed(Need.MANA, -20);
			for (PartyCharacter character : CharacterBank.characters) {
				if (character.getTrueNeed(Need.HUNGER) < 10)
					character.incrementNeed(Need.HUNGER, 10);;
			}
		}
	}
}