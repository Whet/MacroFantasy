package com.mygdx.game.actor.traits;

import java.util.ArrayList;
import java.util.List;

import com.mygdx.game.actor.PartyCharacter;

public abstract class AbstractTrait {

	protected List<TraitFlag> flags;	//Categories which describe the trait and can be used to enable extra event choices.
	protected String description;
	protected String name;
	
	public AbstractTrait() {
		flags = new ArrayList<TraitFlag>();
		name = "";
		description = "";
	}
	
	public boolean hasFlag(TraitFlag flag) {
		for(TraitFlag tf : flags) {
			if(tf.equals(flag)) {
				return true;
			}
		}
		return false;
	}

	public String getName(){
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	//Fancy actions are performed in overriding methods of this.
	public void act(PartyCharacter pc) {}
	
	//Override any of these methods when you want a trait to change it.
	public int getTrueHealth(int baseHealth) { return baseHealth; }
	public int getTrueMaxHealth(int baseMaxHealth) { return baseMaxHealth; }
	public int getTrueMana(int baseMana) { return baseMana; }
	public int getTrueMaxMana(int baseMaxMana) { return baseMaxMana; }
	public int getTrueHunger(int baseHunger) { return baseHunger; }
	public int getTrueMaxHunger(int baseMaxHunger) { return baseMaxHunger; }
	public int getTrueGold(int baseGold) { return baseGold; }
	public int getTrueMaxGold(int baseMaxGold) { return baseMaxGold; }
	public int getTrueHappiness(int baseHappiness) { return baseHappiness; }
	public int getTrueMaxHappiness(int baseMaxHappiness) { return baseMaxHappiness; }
}
