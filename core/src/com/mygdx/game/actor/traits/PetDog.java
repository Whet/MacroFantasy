package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.PartyCharacter;

public class PetDog extends PartyCharacter {
		
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
