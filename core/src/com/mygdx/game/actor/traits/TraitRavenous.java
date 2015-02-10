package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Need;

public class TraitRavenous extends AbstractTrait {

	public TraitRavenous() {
		super();
		name = "Ravenous";
		description = "Your hunger detiorates rapidly.";
		flags.add(TraitFlag.HUNGER);
	}
	
	public void act(PartyCharacter pc) {
		pc.incrementNeed(Need.HUNGER, -5);
	}
}
