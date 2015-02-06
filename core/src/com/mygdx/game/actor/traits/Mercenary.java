package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.PartyCharacter;

public class Mercenary extends PartyCharacter {
		
	public Mercenary()
	{
		super();
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
