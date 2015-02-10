package com.mygdx.game.actor.traits.pools;

import com.mygdx.game.actor.traits.TraitBully;
import com.mygdx.game.actor.traits.TraitFeeble;
import com.mygdx.game.actor.traits.TraitRacist;
import com.mygdx.game.actor.traits.TraitRavenous;

public class PoolGenericNegative extends TraitPool {

	public void populateList() {
		traitPool.add(new TraitFeeble());
		traitPool.add(new TraitBully());
		traitPool.add(new TraitRavenous());
		traitPool.add(new TraitRacist());
	}

}
