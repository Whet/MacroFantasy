package com.mygdx.game.actor.traits.pools;

import com.mygdx.game.actor.traits.TraitBigAppetite;
import com.mygdx.game.actor.traits.TraitFeeble;

public class PoolGeneric extends TraitPool {

	public void populateList() {
		traitPool.add(new TraitBigAppetite());
		traitPool.add(new TraitFeeble());
	}

}
