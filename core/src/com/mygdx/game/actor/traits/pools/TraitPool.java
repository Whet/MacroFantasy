package com.mygdx.game.actor.traits.pools;

import java.util.ArrayList;
import java.util.List;

import com.mygdx.game.actor.traits.AbstractTrait;

public abstract class TraitPool {
	List<AbstractTrait> traitPool;

	public TraitPool(){
		traitPool = new ArrayList<AbstractTrait>();
		populateList();
	}
	
	public abstract void populateList();

	public List<AbstractTrait> getPool(){
		return traitPool;
	}
	
}
