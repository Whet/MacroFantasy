package com.mygdx.game.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.components.primitive.RemoveComponent;

public abstract class Screen {

	protected Engine engine;
	protected OrthographicCamera camera;
	
	public Screen(Engine engine, OrthographicCamera camera) {
		this.engine = engine;
		this.camera = camera;
	}
	
	public final void load() {
		
		do {
			// Remove all old entities before loading new ones
			ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.getFor(RemoveComponent.class));
			
			for(int i = 0; i < entities.size(); i++) {
				engine.removeEntity(entities.get(i));
			}
		}
		while(engine.getEntitiesFor(Family.getFor(RemoveComponent.class)).size() > 0);
		
		loadSystem();
	}

	protected abstract void loadSystem();
	
}
