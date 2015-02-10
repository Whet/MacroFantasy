package com.mygdx.game.actor.traits;

public class TraitCourageous extends AbstractTrait {
	public TraitCourageous() {
		super();
		name = "Courage Aura";
		description = "Your courage allows you to fight longer in battle.";
		flags.add(TraitFlag.HEALTH);
		flags.add(TraitFlag.MAXHEALTH);
		flags.add(TraitFlag.POSITIVE);
	}	
	
	public int getTrueHealth(int baseHealth) {
		return baseHealth + 10;
	}
	
	public int getTrueMaxHealth(int baseMaxHealth) {
		return baseMaxHealth + 10;
	}
}