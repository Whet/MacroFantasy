package com.mygdx.game.actor.traits.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mygdx.game.actor.traits.AbstractTrait;

public abstract class TraitPool {
	List<AbstractTrait> traitPool;
	Random random;

	public TraitPool(){
		random = new Random();
		traitPool = new ArrayList<AbstractTrait>();
		populateList();
	}

	public abstract void populateList();

	public List<AbstractTrait> getPool(){
		return traitPool;
	}
	
	public AbstractTrait getRandomTrait(){
		AbstractTrait trait = traitPool.get(random.nextInt(traitPool.size())); 
		traitPool.remove(trait);
		return trait;
	}

	public boolean isEmpty(){
		return traitPool.isEmpty();
	}


}
