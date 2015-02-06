package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.PartyCharacter;

public class FatBastard extends PartyCharacter {
		
	public FatBastard()
	{
		super();
		trait = "fatBastard";
	}
	
	public String getTrait()
	{
		return trait;
	}
	
	public void checkAlive()
	{
		if (getHealth() < 0)
			setAlive(false, "health");
		else if (getHunger() < 50)
			setAlive(false, "hunger");
	}

}
