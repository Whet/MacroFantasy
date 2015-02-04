package com.mygdx.game.actor;

import java.util.Random;

import com.mygdx.game.actor.traits.FatBastard;
import com.mygdx.game.actor.traits.Fireball;
import com.mygdx.game.actor.traits.FuckingUseless;
import com.mygdx.game.actor.traits.MagicalShield;
import com.mygdx.game.actor.traits.Mercenary;
import com.mygdx.game.actor.traits.MoneyMaker;
import com.mygdx.game.actor.traits.PetDog;
import com.mygdx.game.actor.traits.TotallyNotABadGuy;


public class CharacterTester {



	public static void main (String[] arg) {

		Random rn = new Random();
		Character character = null;

		switch(rn.nextInt(8))
		{
		case 1: character = new TotallyNotABadGuy();
		break;
		case 2: character = new Mercenary();
		break;
		case 3: character = new Fireball();
		break;
		case 4: character = new MoneyMaker();
		break;
		case 5: character = new FatBastard();
		break;
		case 6: character = new MagicalShield();
		break;
		case 7: character = new PetDog();
		break;
		case 8: character = new FuckingUseless();
		break;
		}
		
		character = new FatBastard();
		
		System.out.println("Name "+character.getName());
		System.out.println("Health "+character.getHealth());
		System.out.println("Mana "+character.getMana());
		System.out.println("Race "+character.getRace());
		System.out.println("Hunger "+character.getHunger());
		System.out.println("Trait "+character.getTrait());
		character.subtractHunger(51);
		System.out.println("Hunger "+character.getHunger());


		System.out.println("Health "+character.getHealth());

		if (character.isAlive())
			System.out.println("alive");
		else
			System.out.println("dead");


		System.out.println("Cause: "+character.getCauseOfDeath());

	}

}

