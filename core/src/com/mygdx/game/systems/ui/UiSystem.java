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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.components.primitive.TextureComponent;
import com.mygdx.game.components.ui.UiMouseActivityComponent;
import com.mygdx.game.components.ui.UiPositionComponent;
import com.mygdx.game.entities.ui.UiButtonEntity;
import com.mygdx.game.mouse.Mouse;
import com.mygdx.game.mouse.Mouse.MousePos;

public class UiSystem extends EntitySystem {
	private ImmutableArray<Entity> uiImages;
	private ImmutableArray<Entity> uiButtons;

	private SpriteBatch batch;
	private OrthographicCamera camera;

	private ComponentMapper<UiPositionComponent> pm = ComponentMapper.getFor(UiPositionComponent.class);
	private ComponentMapper<TextureComponent> tm = ComponentMapper.getFor(TextureComponent.class);
	private ComponentMapper<UiMouseActivityComponent> mm = ComponentMapper.getFor(UiMouseActivityComponent.class);
	
	public UiSystem (OrthographicCamera camera) {
		batch = new SpriteBatch();

		this.camera = camera;
	}

	@Override
	public void addedToEngine (Engine engine) {
		uiImages = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, TextureComponent.class));
		uiButtons = engine.getEntitiesFor(Family.getFor(UiPositionComponent.class, TextureComponent.class, UiMouseActivityComponent.class));
	}

	@Override
	public void removedFromEngine (Engine engine) {

	}

	@Override
	public void update (float deltaTime) {
		UiPositionComponent position;
		TextureComponent visual;
		UiMouseActivityComponent mouse;

		camera.update();

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
 		// Draw UI
		for (int i = 0; i < uiImages.size(); ++i) {
			Entity e = uiImages.get(i);

			position = pm.get(e);
			visual = tm.get(e);
			
			batch.draw(visual.region, position.x, position.y);
		}
		// Handle mouse events
		for (int i = 0; i < uiButtons.size(); ++i) {
			Entity e = uiButtons.get(i);

			position = pm.get(e);
			visual = tm.get(e);
			mouse = mm.get(e);
			
			MousePos mousePos = Mouse.getMouse();
			
			boolean isInBounds = position.x <= mousePos.x &&
								 position.y <= mousePos.y &&
								 position.x + visual.region.getRegionWidth() >= mousePos.x  &&
								 position.y + visual.region.getRegionHeight() >= mousePos.y;
								 
			UiButtonEntity btn = (UiButtonEntity) e;
			
			if(isInBounds && mouse.mouseActive){
				btn.mI(mousePos.x, mousePos.y);
		    }
			else if(mouse.mouseActive) {
				btn.mO(mousePos.x, mousePos.y);
			}
			
			if(isInBounds && mouse.mouseActive && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				if(btn.mD(mousePos.x, mousePos.y))
					break;
			}
			if(isInBounds && mouse.mouseActive && !Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
				if(btn.mU(mousePos.x, mousePos.y))
					break;
			}
			
		}

		batch.end();
	}
}
