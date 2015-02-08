package com.mygdx.game.actor.traits;

public class TraitFeeble extends AbstractTrait {
	public TraitFeeble() {
		super();
		description = "Weaker than most, it takes fewer hits to take you down.";
		flags.add(TraitFlag.HEALTH);
	}
	
	public int getTrueHealth(int baseHealth) {
		return baseHealth - 10;
	}
}
