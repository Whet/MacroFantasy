package com.mygdx.game.actor.traits.pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mygdx.game.actor.traits.AbstractTrait;
import com.mygdx.game.actor.traits.TraitFlag;

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
	
	//Positive and neutral traits
	public AbstractTrait getRandomTrait(){
		List<AbstractTrait> traits = new ArrayList<AbstractTrait>();
		for(AbstractTrait trait: traitPool)
		{
			if(trait.hasFlag(TraitFlag.POSITIVE) || trait.hasFlag(TraitFlag.NEUTRAL))
				traits.add(trait);
		}
		AbstractTrait trait = traits.get(random.nextInt(traits.size())); 
		traitPool.remove(trait);
		return trait;
	}
	
	//negative traits
	public AbstractTrait getNegativeTrait(){
		List<AbstractTrait> negativeTraits = new ArrayList<AbstractTrait>();
		for(AbstractTrait trait: traitPool)
		{
			if(trait.hasFlag(TraitFlag.NEGATIVE))
					negativeTraits.add(trait);
		}
		AbstractTrait trait = traitPool.get(random.nextInt(negativeTraits.size())); 
		traitPool.remove(trait);
		return trait;
	}

	//all trauts
	public AbstractTrait getFullRandomTrait(){
		AbstractTrait trait = traitPool.get(random.nextInt(traitPool.size())); 
		traitPool.remove(trait);
		return trait;
	}
	
	public boolean isEmpty(){
		return traitPool.isEmpty();
	}
	
	public boolean isPosNeuEmpty(){
		boolean hasPosNeu = true;
		for(AbstractTrait trait : traitPool)
		{
			if (trait.hasFlag(TraitFlag.POSITIVE) || trait.hasFlag(TraitFlag.NEUTRAL))
				hasPosNeu = false;
		}
		return hasPosNeu;
	}

	public boolean isNegativeEmpty(){
		boolean hasNegative = true;
		for(AbstractTrait trait : traitPool)
		{
			if (trait.hasFlag(TraitFlag.NEGATIVE))
				hasNegative = false;
		}
		return hasNegative;
	}


}
