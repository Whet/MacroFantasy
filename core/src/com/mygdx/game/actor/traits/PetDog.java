package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.Character;

public class PetDog extends Character {
		
	public PetDog()
	{
		super();
		trait = "PetDog";
	}
	
	public String getTrait()
	{
		return trait;
	}
	
	public void subtractHunger(int increment)
	{
		setHunger(getHunger() - (2 * increment));
	}

}
