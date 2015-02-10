package com.mygdx.game.actor.traits.pools;

import com.mygdx.game.actor.traits.TraitBigAppetite;

public class PoolGeneric extends TraitPool {

	public void populateList() {
		traitPool.add(new TraitBigAppetite());
	}

}
