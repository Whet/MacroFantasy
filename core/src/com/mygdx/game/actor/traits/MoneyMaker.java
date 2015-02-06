package com.mygdx.game.actor.traits;

import com.mygdx.game.actor.PartyCharacter;

public class MoneyMaker extends PartyCharacter {
		
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
