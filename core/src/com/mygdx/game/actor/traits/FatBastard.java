package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.Character;

public class FatBastard extends Character {
		
	public FatBastard()
	{
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
