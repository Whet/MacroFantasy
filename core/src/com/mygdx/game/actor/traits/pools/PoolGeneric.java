package com.mygdx.game.actor.traits.pools;

import com.mygdx.game.actor.traits.TraitBigAppetite;
import com.mygdx.game.actor.traits.TraitBully;
import com.mygdx.game.actor.traits.TraitFeeble;
import com.mygdx.game.actor.traits.TraitLoneMaiden;
import com.mygdx.game.actor.traits.TraitMercenary;
import com.mygdx.game.actor.traits.TraitRacist;
import com.mygdx.game.actor.traits.TraitRavenous;

public class PoolGeneric extends TraitPool {

	public void populateList() {
		traitPool.add(new TraitBigAppetite());
		traitPool.add(new TraitFeeble());
		traitPool.add(new TraitBully());
		traitPool.add(new TraitRavenous());
		traitPool.add(new TraitRacist());
		traitPool.add(new TraitLoneMaiden());
		traitPool.add(new TraitMercenary());
	}

}
