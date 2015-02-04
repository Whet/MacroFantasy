package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.Character;

public class MoneyMaker extends Character {
		
	public MoneyMaker()
	{
		super();
		trait = "MoneyMaker";
	}
	
	public String getTrait()
	{
		return trait;
	}

	public void addGold(int increment)
	{
		setGold(getGold() + (2*increment));
	}
}
