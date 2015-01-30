/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mygdx.game.systems.ui;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.components.primitive.TextureComponent;
import com.mygdx.game.components.ui.UiPositionComponent;

public class UiSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;

	private SpriteBatch batch;
	private OrthographicCamera camera;

	private ComponentMapper<UiPositionComponent> pm = ComponentMapper.getFor(UiPositionComponent.class);
	private ComponentMapper<TextureComponent> vm = ComponentMapper.getFor(TextureComponent.class);

	public UiSystem (OrthographicCamera camera) {
		batch = new SpriteBatch();

		this.camera = camera;
	}

	@Override
	public void addedToEngine (Engine engine) {
		entities = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, TextureComponent.class));
	}

	@Override
	public void removedFromEngine (Engine engine) {

	}

	@Override
	public void update (float deltaTime) {
		UiPositionComponent position;
		TextureComponent visual;

		camera.update();

		batch.begin();
		batch.setProjectionMatrix(camera.combined);

		for (int i = 0; i < entities.size(); ++i) {
			Entity e = entities.get(i);

			position = pm.get(e);
			visual = vm.get(e);
			
			if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
				position.x = Gdx.input.getX() - visual.region.getRegionWidth()/2;
				position.y = Gdx.graphics.getHeight() - Gdx.input.getY() - visual.region.getRegionHeight()/2;
		    }

			batch.draw(visual.region, position.x, position.y);
		}

		batch.end();
	}
}
