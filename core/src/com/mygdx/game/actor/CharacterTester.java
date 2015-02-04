package com.mygdx.game.actor;

public class CharacterTester {
	public static void main (String[] arg) {
		Character character = new Character();
		System.out.println("Name "+character.getName());
		System.out.println("Health "+character.getHealth());
		System.out.println("Mana "+character.getMana());
		System.out.println("Race "+character.getRace());
		System.out.println("Hunger "+character.getHunger());
		
		for (String s : character.getTraits())
		{
			System.out.println("Traits " + s);
		}
		
		character.incrementHealth(-200);
		
		System.out.println("Health "+character.getHealth());
		
		if (character.isAlive())
			System.out.println("alive");
		else
			System.out.println("dead");
		
		System.out.println("Cause: "+character.getCauseOfDeath());
				
		
		
	}

}
