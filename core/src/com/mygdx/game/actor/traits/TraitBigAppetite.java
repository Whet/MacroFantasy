package com.mygdx.game.actor.traits;

public class TraitBigAppetite extends AbstractTrait {

	public TraitBigAppetite() {
		super();
		description = "A big belly means it takes more to fill you up.";
		flags.add(TraitFlag.HUNGER);
	}
	
	public int getTrueMaxHunger(int maxHunger) {
		return maxHunger + 10;
	}
}
