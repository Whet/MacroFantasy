package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.Character;

public class Mercenary extends Character {
		
	public Mercenary()
	{
		trait = "Mercenary";
	}
	
	public String getTrait()
	{
		return trait;
	}
	
	public void checkAlive()
	{
		if (getHealth() < 0)
			setAlive(false, "health");
		else if (getHunger() < 0)
			setAlive(false, "hunger");
		else if (getGold() < 0)
			setAlive(false, "gold");
	}
}
