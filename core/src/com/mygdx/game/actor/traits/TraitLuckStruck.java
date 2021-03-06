package com.mygdx.game.actor.traits;

public class TraitLuckStruck extends AbstractTrait {

	public TraitLuckStruck() {
		super();
		name = "Luck-Struck";
		description = "You are inexplicably lucky.";
		flags.add(TraitFlag.LUCK);
		flags.add(TraitFlag.POSITIVE);
	}

	public int getLuck(int baseLuck) {
		return baseLuck + 30;
	}

}
