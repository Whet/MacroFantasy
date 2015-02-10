package com.mygdx.game.actor.traits;

import java.util.Random;

import com.mygdx.game.actor.CharacterBank;
import com.mygdx.game.actor.PartyCharacter;
import com.mygdx.game.actor.enums.Need;

public class TraitBully extends AbstractTrait {

	Random rn;
	
	public TraitBully() {
		super();
		name = "Bully";
		description = "This character will pick on the party member with the lowest hit-points every so often.";
		flags.add(TraitFlag.HAPPINESS);
		rn = new Random();
	}
	
	public void act(PartyCharacter pc) {
		PartyCharacter weakest = CharacterBank.characters.get(0);
		for (PartyCharacter character : CharacterBank.characters) {
			if (character != weakest && character.getTrueNeed(Need.HEALTH) < weakest.getTrueNeed(Need.HEALTH))
			{
				weakest = character;
			}
			else if (character != weakest && character.getTrueNeed(Need.HEALTH) == weakest.getTrueNeed(Need.HEALTH))
			{
				//if two have equal health then 50% of chance of choosing one
				if (rn.nextInt(2) == 0)
					weakest = character;
			}
		}
		//5% chance of bullying character with lowest hp
		if (rn.nextInt(20) == 0)
		{
			weakest.incrementNeed(Need.HAPPINESS, -40);
			pc.incrementNeed(Need.HAPPINESS, 20);
			System.out.println(pc.getName() + " bullies " + weakest.getName());
		}
	}
}
