package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.PartyCharacter;

public class MagicalShield extends PartyCharacter {
		
	public MagicalShield()
	{
		super();
		trait = "MagicalShield";
	}
	
	public String getTrait()
	{
		return trait;
	}
	
	public void subtractHealth(int increment)
	{
		if (getMana() > increment)
		{
			subtractMana(increment);
		}
		else
		{
			setHealth(getHealth() - increment);
		}
	}

}
