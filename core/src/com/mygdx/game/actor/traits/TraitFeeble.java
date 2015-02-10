package com.mygdx.game.actor.traits;

public class TraitFeeble extends AbstractTrait {
	public TraitFeeble() {
		super();
		name = "Feeble";
		description = "Weaker than most, it takes fewer hits to take you down.";
		flags.add(TraitFlag.HEALTH);
		flags.add(TraitFlag.MAXHEALTH);
	}
	
	public int getTrueHealth(int baseHealth) {
		return baseHealth - 10;
	}
	
	public int getTrueMaxHealth(int baseMaxHealth) {
		return baseMaxHealth - 10;
	}
}
