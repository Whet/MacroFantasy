package com.mygdx.game.actor.traits.pools;

import com.mygdx.game.actor.traits.TraitConjureFeast;


public class PoolMagic extends TraitPool {

	public void populateList() {
		traitPool.add(new TraitConjureFeast());
	}

}
