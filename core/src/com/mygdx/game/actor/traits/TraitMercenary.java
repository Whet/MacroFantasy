package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Need;

public class TraitMercenary extends AbstractTrait {
	public TraitMercenary() {
		super();
		name = "Mercenary";
		description = "You make 1 gold per turn but will leave the party if your gold dips below 10.";
		flags.add(TraitFlag.GOLD);
		flags.add(TraitFlag.NEUTRAL);
	}
	
	public void act(PartyCharacter pc) {
		pc.setVitalNeed(Need.GOLD, true, 10);
		pc.incrementNeed(Need.GOLD, 1);
	}
}
