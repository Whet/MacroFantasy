package com.mygdx.game.actor.traits.pools;

import com.mygdx.game.actor.traits.TraitLoneMaiden;

public class PoolFemale extends TraitPool {

	public void populateList() {
		traitPool.add(new TraitLoneMaiden());
	}

}
