package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.Character;

public class MagicalShield extends Character {
		
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
